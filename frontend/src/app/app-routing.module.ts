import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginFormComponent } from './page/login-form/login-form.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { DriverListComponent } from './page/driver/driver-list/driver-list.component';
import { AddDriverComponent } from './page/driver/add-driver/add-driver.component';
import { UpdateDriverComponent } from './page/driver/update-driver/update-driver.component';
import { AddLocationComponent } from './page/location/add-location/add-location.component';
import { LocationListComponent } from './page/location/location-list/location-list.component';
import { UpdateLocationComponent } from './page/location/update-location/update-location.component';
import { AddLocationPortComponent } from './page/location-port/add-location-port/add-location-port.component';
import { LocationPortListComponent } from './page/location-port/location-port-list/location-port-list.component';
import { UpdateLocationPortComponent } from './page/location-port/update-location-port/update-location-port.component';
import { UserListComponent } from './page/user/user-list/user-list.component';
import { UpdateUserComponent } from './page/user/update-user/update-user.component';
import { AddUserComponent } from './page/user/add-user/add-user.component';
import { AddStatusComponent } from './page/status/add-status/add-status.component';
import { StatusListComponent } from './page/status/status-list/status-list.component';
import { UpdateStatusComponent } from './page/status/update-status/update-status.component';
import { AddVehicleTypeComponent } from './page/vehicle-type/add-vehicle-type/add-vehicle-type.component';
import { UpdateVehicleTypeComponent } from './page/vehicle-type/update-vehicle-type/update-vehicle-type.component';
import { VehicleTypeListComponent } from './page/vehicle-type/vehicle-type-list/vehicle-type-list.component';
import { ProductFieldListComponent } from './page/product-field/product-field-list/product-field-list.component';
import { ProductFieldAddComponent } from './page/product-field/product-field-add/product-field-add.component';
import { ProductFieldUpdateComponent } from './page/product-field/product-field-update/product-field-update.component';
import { AuthGuard } from './auth-service/authguard/authguard';
import { PermissionListComponent } from './page/permission/permission-list/permission-list.component';
import { AddPermissionComponent } from './page/permission/add-permission/add-permission.component';
import { EditPermissionComponent } from './page/permission/edit-permission/edit-permission.component';
import { RoleListComponent } from './page/role/role-list/role-list.component';
import { AddRoleComponent } from './page/role/add-role/add-role.component';
import { EditRoleComponent } from './page/role/edit-role/edit-role.component';
import { DomesticShippingListComponent } from './page/shipping-order/domestic/domestic-shipping-list/domestic-shipping-list.component';
import { DomesticShippingOrderHistoryComponent } from './page/shipping-order/domestic/domestic-shipping-order-history/domestic-shipping-order-history.component';
import { AddDomesticShippingComponent } from './page/shipping-order/domestic/add-domestic-shipping/add-domestic-shipping.component';
import { UpdateDomesticShippingComponent } from './page/shipping-order/domestic/update-domestic-shipping/update-domestic-shipping.component';
import { TileComponent } from './page/shipping-order/international/by-road/tile/tile.component';
import { InternationalShippingListComponent } from './page/shipping-order/international/by-road/international-shipping-list-road/international-shipping-list.component';
import { InternationalShippingOrderHistoryComponent } from './page/shipping-order/international/by-road/international-shipping-order-history-by-road/international-shipping-order-history.component';
import { AddInternationalShippingComponent } from './page/shipping-order/international/by-road/add-international-shipping-by-road/add-international-shipping.component';
import { UpdateInternationalShippingComponent } from './page/shipping-order/international/by-road/update-international-shipping-by-road/update-international-shipping.component';
import { InternationalShipmentListAirComponent } from './page/shipping-order/international/by-air/international-shipment-list-air/international-shipment-list-air.component';
import { InternationalShipmentOrderHistoryByAirComponent } from './page/shipping-order/international/by-air/international-shipment-order-history-by-air/international-shipment-order-history-by-air.component';
import { AddInternationalShipmentByRoadComponent } from './page/shipping-order/international/by-air/add-international-shipment-by-air/add-international-shipment-by-road.component';
import { UpdateInternationalShipmentByAirComponent } from './page/shipping-order/international/by-air/update-international-shipment-by-air/update-international-shipment-by-air.component';
import { DomesticSummaryComponent } from './page/bounds/domestic/domestic-summary/domestic-summary.component';
import { InternationalSummaryByAirComponent } from './page/bounds/international/international-summary-by-air/international-summary-by-air.component';
import { InternationalSummaryByRoadComponent } from './page/bounds/international/international-summary-by-road/international-summary-by-road.component';

const routes: Routes = [
  {
    path:'',
    component:DashboardComponent,
    canActivate:[AuthGuard]
  },
  {
    path:'home',
    component:DashboardComponent,
    canActivate:[AuthGuard]
  },
  {
    path:'product-field',
    component:ProductFieldListComponent,
    canActivate:[AuthGuard]
  },
  {
    path:'add-product-field',
    component:ProductFieldAddComponent,
    canActivate:[AuthGuard]
  },
  {
    path:'edit-product-field',
    component:ProductFieldUpdateComponent,
    canActivate:[AuthGuard]
  },
  {
    path:'driver',
    component:DriverListComponent,
    canActivate:[AuthGuard]
  },
  {
    path:'add-driver',
    component:AddDriverComponent,
    canActivate:[AuthGuard]
  },
  {
    path:'edit-driver',
    component:UpdateDriverComponent,
    canActivate:[AuthGuard]
  },
  {
    path:'location',
    component:LocationListComponent,
    canActivate:[AuthGuard]
  },
  {
    path:'add-location',
    component:AddLocationComponent,
    canActivate:[AuthGuard]
  },
  {
    path:'edit-location',
    component:UpdateLocationComponent,
    canActivate:[AuthGuard]
  },
  {
    path:'location-port',
    component:LocationPortListComponent,
    canActivate:[AuthGuard]
  },
  {
    path:'add-location-port',
    component:AddLocationPortComponent,
    canActivate:[AuthGuard]
  },
  {
    path:'edit-location-port',
    component:UpdateLocationPortComponent,
    canActivate:[AuthGuard]
  },
  {
    path:'user',
    component:UserListComponent,
    canActivate:[AuthGuard]
  },
  {
    path:'add-user',
    component:AddUserComponent,
    canActivate:[AuthGuard]
  },
  {
    path:'edit-user',
    component:UpdateUserComponent,
    canActivate:[AuthGuard]
  },
  {
    path:'status',
    component:StatusListComponent,
    canActivate:[AuthGuard]
  },
  {
    path:'add-status',
    component:AddStatusComponent,
    canActivate:[AuthGuard]
  },
  {
    path:'edit-status',
    component:UpdateStatusComponent,
    canActivate:[AuthGuard]
  },
  {
    path:'vehicle-type',
    component:VehicleTypeListComponent,
    canActivate:[AuthGuard]
  },
  {
    path:'add-vehicle-type',
    component:AddVehicleTypeComponent,
    canActivate:[AuthGuard]
  },
  {
    path:'edit-vehicle-type',
    component:UpdateVehicleTypeComponent,
    canActivate:[AuthGuard]
  },
  {
    path:'permission',
    component:PermissionListComponent,
    canActivate:[AuthGuard]
  },
  {
    path:'add-permission',
    component:AddPermissionComponent,
    canActivate:[AuthGuard]
  },
  {
    path:'edit-permission',
    component:EditPermissionComponent,
    canActivate:[AuthGuard]
  },
  {
    path:'role',
    component:RoleListComponent,
    canActivate:[AuthGuard]
  },
  {
    path:'add-role',
    component:AddRoleComponent,
    canActivate:[AuthGuard]
  },
  {
    path:'edit-role',
    component:EditRoleComponent,
    canActivate:[AuthGuard]
  },
  {
    path:'domestic-shipping',
    component:DomesticShippingListComponent,
    canActivate:[AuthGuard]
  },
  {
    path:'domestic-shipping-history',
    component:DomesticShippingOrderHistoryComponent,
    canActivate:[AuthGuard]
  },
  {
    path:'add-domestic-shipping',
    component:AddDomesticShippingComponent,
    canActivate:[AuthGuard]
  },
  {
    path:'update-domestic-shipping',
    component:UpdateDomesticShippingComponent,
    canActivate:[AuthGuard]
  },
  {
    path:'international-tile',
    component:TileComponent,
    canActivate:[AuthGuard]
  },
  {
    path:'international-shipment-by-road',
    component:InternationalShippingListComponent,
    canActivate:[AuthGuard]
  },
  {
    path:'international-shipment-history-by-road',
    component:InternationalShippingOrderHistoryComponent,
    canActivate:[AuthGuard]
  },
  {
    path:'add-international-shipment-by-road',
    component:AddInternationalShippingComponent,
    canActivate:[AuthGuard]
  },
  {
    path:'update-international-shipment-by-road',
    component:UpdateInternationalShippingComponent,
    canActivate:[AuthGuard]
  },
  
  {
    path:'international-shipment-by-air',
    component:InternationalShipmentListAirComponent,
    canActivate:[AuthGuard]
  },
  {
    path:'international-shipment-history-by-air',
    component:InternationalShipmentOrderHistoryByAirComponent,
    canActivate:[AuthGuard]
  },
  {
    path:'add-international-shipment-by-air',
    component:AddInternationalShipmentByRoadComponent,
    canActivate:[AuthGuard]
  },
  {
    path:'update-international-shipment-by-air',
    component:UpdateInternationalShipmentByAirComponent,
    canActivate:[AuthGuard]
  },
  {
    path:'domestic-summary',
    component:DomesticSummaryComponent,
    canActivate:[AuthGuard]
  },
  {
    path:'international-summary-by-air',
    component:InternationalSummaryByAirComponent,
    canActivate:[AuthGuard]
  },
  {
    path:'international-summary-by-road',
    component:InternationalSummaryByRoadComponent,
    canActivate:[AuthGuard]
  },

  {
    path:'login',
    component:LoginFormComponent
  }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {

 }
