const { createApp } = Vue;
const api = axios.create({ baseURL: "http://localhost:8080/api" });

createApp({
  data() {
    return {
      orders: [],
      showOrderModal: false,
      orderForm: {
        accountId: '',
        amount: '',
        receiverName: '',
        receiverPhone: '',
        receiverAddress: '',
        status: 'PAID'
      }
    };
  },
  computed: {
    activeOrders() {
      return this.orders.filter(o => o.status !== 'DELIVERED');
    },
    completedOrders() {
      return this.orders.filter(o => o.status === 'DELIVERED');
    }
  },
  methods: {
    async loadOrders() {
      try {
        const res = await api.get("/orders");
        this.orders = res.data;
      } catch (error) {
        console.error("加载订单数据失败:", error);
        alert("加载数据失败，请检查网络连接");
      }
    },
    showAddOrderModal() {
      this.orderForm = {
        accountId: '',
        amount: '',
        receiverName: '',
        receiverPhone: '',
        receiverAddress: '',
        status: 'PAID'
      };
      this.showOrderModal = true;
    },
    closeOrderModal() {
      this.showOrderModal = false;
      this.orderForm = {
        accountId: '',
        amount: '',
        receiverName: '',
        receiverPhone: '',
        receiverAddress: '',
        status: 'PAID'
      };
    },
    async saveOrder() {
      try {
        await api.post('/orders', this.orderForm);
        alert('订单添加成功');
        this.closeOrderModal();
        await this.loadOrders();
      } catch (error) {
        console.error('保存订单失败:', error);
        alert('保存失败，请重试');
      }
    },
    async shipOrder(id) {
      if (!confirm("确定要将此订单标记为已发货吗？")) {
        return;
      }
      try {
        await api.post(`/orders/${id}/ship`);
        await this.loadOrders();
        alert("订单发货状态更新成功");
      } catch (error) {
        console.error("更新订单状态失败:", error);
        alert("更新失败，请重试");
      }
    },
    async completeOrder(id) {
      if (!confirm("确定要将此订单标记为已完成吗？")) {
        return;
      }
      try {
        await api.post(`/orders/${id}/complete`);
        await this.loadOrders();
        alert("订单已完成");
      } catch (error) {
        console.error("完成订单失败:", error);
        alert("完成失败，请重试");
      }
    },
    async deleteOrder(id) {
      if (!confirm("确定要删除此订单吗？此操作不可恢复。")) {
        return;
      }
      try {
        await api.delete(`/orders/${id}`);
        await this.loadOrders();
        alert("订单删除成功");
      } catch (error) {
        console.error("删除订单失败:", error);
        alert("删除失败，请重试");
      }
    },
    getStatusClass(status) {
      const statusMap = {
        'PAID': 'status-paid',
        'SHIPPED': 'status-shipped',
        'DELIVERED': 'status-delivered'
      };
      return statusMap[status] || 'status-default';
    },
    getStatusText(status) {
      const statusMap = {
        'PAID': '已付款',
        'SHIPPED': '已发货',
        'DELIVERED': '已完成'
      };
      return statusMap[status] || status;
    }
  },
  async mounted() {
    await this.loadOrders();
  }
}).mount("#app");