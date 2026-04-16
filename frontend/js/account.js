const { createApp } = Vue;
const api = axios.create({ baseURL: "http://localhost:8080/api" });

createApp({
  data() {
    return {
      accounts: [],
      searchUsername: "",
      showModal: false,
      isEditMode: false,
      formData: {
        id: null,
        username: "",
        phone: "",
        email: "",
        password: "",
        address: ""
      }
    };
  },
  methods: {
    async loadAccounts() {
      try {
        const res = await api.get("/accounts");
        this.accounts = res.data;
      } catch (error) {
        console.error("加载用户数据失败:", error);
        alert("加载数据失败，请检查网络连接");
      }
    },
    async searchUsers() {
      if (!this.searchUsername.trim()) {
        await this.loadAccounts();
        return;
      }
      try {
        const res = await api.get(`/accounts/search?username=${encodeURIComponent(this.searchUsername)}`);
        this.accounts = res.data;
      } catch (error) {
        console.error("搜索用户失败:", error);
        alert("搜索失败，请重试");
      }
    },
    showAddModal() {
      this.isEditMode = false;
      this.formData = {
        id: null,
        username: "",
        phone: "",
        email: "",
        password: "",
        address: ""
      };
      this.showModal = true;
    },
    showEditModal(account) {
      this.isEditMode = true;
      this.formData = {
        id: account.id,
        username: account.username,
        phone: account.phone,
        email: account.email,
        password: "",
        address: account.address
      };
      this.showModal = true;
    },
    closeModal() {
      this.showModal = false;
      this.formData = {
        id: null,
        username: "",
        phone: "",
        email: "",
        password: "",
        address: ""
      };
    },
    async saveAccount() {
      try {
        if (this.isEditMode) {
          await api.put(`/accounts/${this.formData.id}`, this.formData);
          alert("用户更新成功");
        } else {
          await api.post("/accounts", this.formData);
          alert("用户添加成功");
        }
        this.closeModal();
        await this.loadAccounts();
      } catch (error) {
        console.error("保存用户失败:", error);
        alert("保存失败，请重试");
      }
    },
    async deleteAccount(id) {
      if (!confirm("确定要删除该用户吗？此操作不可恢复。")) {
        return;
      }
      try {
        await api.delete(`/accounts/${id}`);
        alert("用户删除成功");
        await this.loadAccounts();
      } catch (error) {
        console.error("删除用户失败:", error);
        alert("删除失败，请重试");
      }
    },
    async resetPwd(id) {
      if (!confirm("确定要重置该用户的密码吗？新密码将设置为123456。")) {
        return;
      }
      try {
        const response = await api.post(`/accounts/${id}/reset-password`, {
          newPassword: "123456"
        });
        if (response.data.success) {
          alert(`密码重置成功！新密码为: ${response.data.newPassword}`);
        } else {
          alert(`重置失败: ${response.data.message}`);
        }
      } catch (error) {
        console.error("重置密码失败:", error);
        alert("重置失败，请重试");
      }
    }
  },
  async mounted() {
    await this.loadAccounts();
  }
}).mount("#app");