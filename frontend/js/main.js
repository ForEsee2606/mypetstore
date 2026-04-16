const { createApp } = Vue;
const api = axios.create({ baseURL: "http://localhost:8080/api" });

createApp({
  data() {
    return {
      tab: "products",
      categories: [],
      productTypes: [],
      items: [],
      accounts: [],
      orders: [],
      searchUsername: ""
    };
  },
  methods: {
    async loadProducts() {
      const [cRes, tRes, iRes] = await Promise.all([
        api.get("/products/categories"),
        api.get("/products/types"),
        api.get("/products/items")
      ]);
      this.categories = cRes.data;
      this.productTypes = tRes.data;
      this.items = iRes.data;
    },
    async publishItem(id) {
      await api.patch(`/products/items/${id}/publish`);
      await this.loadProducts();
    },
    async loadAccounts() {
      const res = await api.get("/accounts");
      this.accounts = res.data;
    },
    async resetPwd(id) {
      await api.post(`/accounts/${id}/reset-password`, { newPassword: "123456" });
      alert("密码已重置为 123456");
    },

    async searchUsers() {
      if (!this.searchUsername.trim()) {
        await this.loadAccounts();
        return;
      }
      const res = await api.get(`/accounts/search?username=${encodeURIComponent(this.searchUsername)}`);
      this.accounts = res.data;
    },
    async loadOrders() {
      const res = await api.get("/orders");
      this.orders = res.data;
    },
    async shipOrder(id) {
      await api.post(`/orders/${id}/ship`);
      await this.loadOrders();
    }
  },
  async mounted() {
    await Promise.all([this.loadProducts(), this.loadAccounts(), this.loadOrders()]);
  }
}).mount("#app");