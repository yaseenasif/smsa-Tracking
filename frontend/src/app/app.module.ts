import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { UpdateShippingOrderComponent } from './page/shipping-order/update/update-shipping-order.component';
import { LoginFormComponent } from './page/login-form/login-form.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { ReactiveFormsModule } from '@angular/forms';
import { DashboardHeadComponent } from './components/dashboard-head/dashboard-head.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { SidebarComponent } from './components/sidebar/sidebar.component';
import { ShippingOrderHistoryComponent } from './page/shipping-order/shipping-order-history/shipping-order-history.component';
import { UpdateDriverComponent } from './page/driver/update-driver/update-driver.component';
import { AddDriverComponent } from './page/driver/add-driver/add-driver.component';
import { DriverListComponent } from './page/driver/driver-list/driver-list.component';
import { AddLocationComponent } from './page/location/add-location/add-location.component';
import { UpdateLocationComponent } from './page/location/update-location/update-location.component';
import { LocationListComponent } from './page/location/location-list/location-list.component';
import { LocationPortListComponent } from './page/location-port/location-port-list/location-port-list.component';
import { AddLocationPortComponent } from './page/location-port/add-location-port/add-location-port.component';
import { UpdateLocationPortComponent } from './page/location-port/update-location-port/update-location-port.component';
import { UserListComponent } from './page/user/user-list/user-list.component';
import { AddUserComponent } from './page/user/add-user/add-user.component';
import { UpdateUserComponent } from './page/user/update-user/update-user.component';
import { AddShippingComponent } from './page/shipping-order/add-shipping/add-shipping.component';
import { ShippingListComponent } from './page/shipping-order/shipping-list/shipping-list.component';
import { StatusListComponent } from './page/status/status-list/status-list.component';
import { AddStatusComponent } from './page/status/add-status/add-status.component';
import { UpdateStatusComponent } from './page/status/update-status/update-status.component';
import { VehicleTypeListComponent } from './page/vehicle-type/vehicle-type-list/vehicle-type-list.component';
import { AddVehicleTypeComponent } from './page/vehicle-type/add-vehicle-type/add-vehicle-type.component';
import { UpdateVehicleTypeComponent } from './page/vehicle-type/update-vehicle-type/update-vehicle-type.component';


@NgModule({
  declarations: [
    AppComponent,
    UpdateShippingOrderComponent,
    LoginFormComponent,
    DashboardHeadComponent,
    DashboardComponent,
    SidebarComponent,
    ShippingOrderHistoryComponent,
    UpdateDriverComponent,
    AddDriverComponent,
    DriverListComponent,
    AddLocationComponent,
    UpdateLocationComponent,
    LocationListComponent,
    LocationPortListComponent,
    AddLocationPortComponent,
    UpdateLocationPortComponent,
    UserListComponent,
    AddUserComponent,
    UpdateUserComponent,
    AddShippingComponent,
    ShippingListComponent,
    StatusListComponent,
    AddStatusComponent,
    UpdateStatusComponent,
    VehicleTypeListComponent,
    AddVehicleTypeComponent,
    UpdateVehicleTypeComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgbModule,
    ReactiveFormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
