import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginFormComponent } from './page/login-form/login-form.component';
import { UpdateShippingOrderComponent } from './page/shipping-order/update/update-shipping-order.component';
import { ShippingOrderHistoryComponent } from './page/shipping-order/shipping-order-history/shipping-order-history.component';
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

const routes: Routes = [
  {
    path:'',
    component:DashboardComponent
  },
  {
    path:'driver',
    component:DriverListComponent
  },
  {
    path:'add-driver',
    component:AddDriverComponent
  },
  {
    path:'edit-driver',
    component:UpdateDriverComponent
  },
  {
    path:'location',
    component:LocationListComponent
  },
  {
    path:'add-location',
    component:AddLocationComponent
  },
  {
    path:'edit-location',
    component:UpdateLocationComponent
  },
  {
    path:'location-port',
    component:LocationPortListComponent
  },
  {
    path:'add-location-port',
    component:UpdateLocationPortComponent
  },
  {
    path:'edit-location-port',
    component:UpdateLocationPortComponent
  },
  {
    path:'user',
    component:UserListComponent
  },
  {
    path:'add-user',
    component:AddUserComponent
  },
  {
    path:'edit-user',
    component:UpdateUserComponent
  },
  {
    path:'status',
    component:StatusListComponent
  },
  {
    path:'add-status',
    component:AddStatusComponent
  },
  {
    path:'edit-status',
    component:UpdateStatusComponent
  },
  {
    path:'vehicle-type',
    component:VehicleTypeListComponent
  },
  {
    path:'add-vehicle-type',
    component:AddVehicleTypeComponent
  },
  {
    path:'edit-vehicle-type',
    component:UpdateVehicleTypeComponent
  },
  {
    path:'shipping',
    component:UpdateShippingOrderComponent
  },
  {
    path:'shipping-order-history',
    component:ShippingOrderHistoryComponent
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
