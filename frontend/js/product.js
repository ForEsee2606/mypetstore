const { createApp } = Vue;
const api = axios.create({ baseURL: "http://localhost:8080/api" });

createApp({
  data() {
    return {
      categories: [],
      productTypes: [],
      items: [],
      activeCategory: null,
      selectedCategory: null,
      selectedCategoryName: '',
      showItemModal: false,
      isEditMode: false,
      itemForm: {
        id: null,
        name: '',
        productTypeId: null,
        price: '',
        stock: '',
        status: 0
      }
    };
  },
  computed: {
    foodTypes() {
      return this.productTypes.filter(t => t.categoryId === 1);
    },
    supplyTypes() {
      return this.productTypes.filter(t => t.categoryId === 2);
    },
    filteredItems() {
      if (!this.selectedCategory) return this.items;
      if (this.selectedCategory.type === 'category') {
        const typeIds = this.productTypes
          .filter(t => t.categoryId === this.selectedCategory.id)
          .map(t => t.id);
        return this.items.filter(i => typeIds.includes(i.productTypeId));
      } else {
        return this.items.filter(i => i.productTypeId === this.selectedCategory.id);
      }
    }
  },
  methods: {
    async loadProducts() {
      try {
        const [cRes, tRes, iRes] = await Promise.all([
          api.get("/products/categories"),
          api.get("/products/types"),
          api.get("/products/items")
        ]);
        this.categories = cRes.data;
        this.productTypes = tRes.data;
        this.items = iRes.data;
      } catch (error) {
        console.error("加载商品数据失败:", error);
        alert("加载数据失败，请检查网络连接");
      }
    },
    showDropdown(categoryId) {
      this.activeCategory = categoryId;
    },
    hideDropdown() {
      this.activeCategory = null;
    },
    selectType(type) {
      this.selectedCategory = { ...type, type: 'productType' };
      this.selectedCategoryName = type.name;
      this.activeCategory = null;
    },
    selectCategory(categoryId) {
      const category = this.categories.find(c => c.id === categoryId);
      if (category) {
        this.selectedCategory = { ...category, type: 'category' };
        this.selectedCategoryName = category.name;
        this.activeCategory = null;
      }
    },
    showAllItems() {
      this.selectedCategory = null;
      this.selectedCategoryName = '全部商品';
    },
    showAddItemModal() {
      this.isEditMode = false;
      this.itemForm = {
        id: null,
        name: '',
        productTypeId: null,
        price: '',
        stock: '',
        status: 0
      };
      this.showItemModal = true;
    },
    showEditItemModal(item) {
      this.isEditMode = true;
      this.itemForm = {
        id: item.id,
        name: item.name,
        productTypeId: item.productTypeId,
        price: item.price,
        stock: item.stock,
        status: item.status
      };
      this.showItemModal = true;
    },
    closeItemModal() {
      this.showItemModal = false;
      this.itemForm = {
        id: null,
        name: '',
        productTypeId: null,
        price: '',
        stock: '',
        status: 0
      };
    },
    async saveItem() {
      try {
        if (this.isEditMode) {
          await api.put(`/products/items/${this.itemForm.id}`, this.itemForm);
          alert('商品更新成功');
        } else {
          await api.post('/products/items', this.itemForm);
          alert('商品添加成功');
        }
        this.closeItemModal();
        await this.loadProducts();
      } catch (error) {
        console.error('保存商品失败:', error);
        alert('保存失败，请重试');
      }
    },
    async deleteItem(id) {
      if (!confirm('确定要删除该商品吗？此操作不可恢复。')) {
        return;
      }
      try {
        await api.delete(`/products/items/${id}`);
        alert('商品删除成功');
        await this.loadProducts();
      } catch (error) {
        console.error('删除商品失败:', error);
        alert('删除失败，请重试');
      }
    },
    getCategoryName(categoryId) {
      const category = this.categories.find(c => c.id === categoryId);
      return category ? category.name : '';
    },
    async publishItem(id) {
      try {
        await api.patch(`/products/items/${id}/publish`);
        await this.loadProducts();
        alert("商品上架成功");
      } catch (error) {
        console.error("上架商品失败:", error);
        alert("上架失败，请重试");
      }
    },
    async unpublishItem(id) {
      try {
        await api.patch(`/products/items/${id}/unpublish`);
        await this.loadProducts();
        alert("商品下架成功");
      } catch (error) {
        console.error("下架商品失败:", error);
        alert("下架失败，请重试");
      }
    }
  },
  async mounted() {
    await this.loadProducts();
  }
}).mount("#app");