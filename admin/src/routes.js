//
import Home from './views/Home.vue'
import NotFound from './views/404.vue'
// import Info from './views/Info.vue'
import User from './views/User.vue'
import Login from './views/Login.vue'
import Area from './views/Area.vue'
import Material from './views/Material.vue'
import Customer from './views/Customer.vue'
import Dashboard from './views/Dashboard.vue'
import Product from './views/Product.vue'
// import ProductForm from './views/ProductForm.vue'
// import User from './views/Info.vue'
import Order from './views/Order.vue'
import OrderForm from './views/OrderForm.vue'
import DetailOrder from './views/DetailOrder.vue'
import ReportOrder from './views/ReportOrder.vue'
import ReportMaterial from './views/ReportMaterial.vue'
// { path: '/hidden', component: Info, name: 'Customer', hidden: true  },


let routes = [
	{
        path: '/login',
        component: Login,
        name: '',
        hidden: true
    },
	{
        path: '/',
        component: Home,
				name: 'Dashboard',
        iconCls: 'fa fa-bar-chart',
        children: [
            { path: '/', component: Dashboard, name: 'Dashboard', iconCls:'fa fa-bar-chart'}
        ]
    },
	{
        path: '/',
        component: Home,
				name: 'Master',
        iconCls: 'fa fa-tags',
        children: [
					{ path: '/area', component: Area, name: 'Area', iconCls:'fa fa-building'},
					{ path: '/user', component: User, name: 'User', iconCls:'fa fa-sitemap'},
					{ path: '/product', component: Product, name: 'Product', iconCls:'fa fa-tags'},
					{ path: '/material', component: Material, name: 'Bahan', iconCls:'fa fa-tags'},
					{ path: '/customer', component: Customer, name: 'Customer', iconCls:'fa fa-user'},
					// { path: '/customer/:id', component: CustomerForm, name: 'CustomerForm' , hidden: true },
					// { path: '/product/:id', component: ProductForm, name: 'ProductForm' , hidden: true },
					// { path: '/ordercategory', component: OrderCategory, name: 'Order Category', iconCls:'fa fa-tags'},
        ]
    },
	{
        path: '/',
        component: Home,
				name: 'Order',
        iconCls: 'fa fa-credit-card-alt',
        children: [
            { path: '/order', component: Order, name: 'Order', iconCls:'fa fa-credit-card-alt'},
						{ path: '/order/:id', component: OrderForm, name: 'OrderForm' , hidden: true },
						{ path: '/detailorder', component: DetailOrder, name: 'Realisasi Order', iconCls:'fa fa-credit-card-alt'},
        ]
    },
	{
        path: '/',
        component: Home,
				name: 'Reports',
        iconCls: 'fa fa-file-text',
        children: [
            { path: '/reportorder', component: ReportOrder, name: 'Orders Report', iconCls:'fa fa-file-text'},
			{ path: '/reportmaterial', component: ReportMaterial, name: 'Pemakaian Bahan', iconCls:'fa fa-file-text'},
        ]
    },
	// {
    //     path: '/',
    //     component: Home,
	// 			name: 'Setting',
    //     iconCls: 'fa fa-id-card-o',
    //     children: [
    //         // { path: '/user', component: User, name: 'User', iconCls:'fa fa-users'},
    //     ]
    // },
	{
        path: '/404',
        component: NotFound,
        name: '',
        hidden: true
    },
    {
        path: '*',
        hidden: true,
        redirect: { path: '/404' }
    }
];

export default routes;
