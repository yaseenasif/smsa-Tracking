import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginFormComponent } from './page/login-form/login-form.component';
import { ReactiveFormsModule } from '@angular/forms';
import { DashboardHeadComponent } from './components/dashboard-head/dashboard-head.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { SidebarComponent } from './components/sidebar/sidebar.component';
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
import { StatusListComponent } from './page/status/status-list/status-list.component';
import { AddStatusComponent } from './page/status/add-status/add-status.component';
import { UpdateStatusComponent } from './page/status/update-status/update-status.component';
import { VehicleTypeListComponent } from './page/vehicle-type/vehicle-type-list/vehicle-type-list.component';
import { AddVehicleTypeComponent } from './page/vehicle-type/add-vehicle-type/add-vehicle-type.component';
import { UpdateVehicleTypeComponent } from './page/vehicle-type/update-vehicle-type/update-vehicle-type.component';
import { PermissionListComponent } from './page/permission/permission-list/permission-list.component';
import { AddPermissionComponent } from './page/permission/add-permission/add-permission.component';
import { EditPermissionComponent } from './page/permission/edit-permission/edit-permission.component';
import { RoleListComponent } from './page/role/role-list/role-list.component';
import { AddRoleComponent } from './page/role/add-role/add-role.component';
import { EditRoleComponent } from './page/role/edit-role/edit-role.component';
import { CommonModule } from '@angular/common';
import { ProductFieldListComponent } from './page/product-field/product-field-list/product-field-list.component';
import { ProductFieldAddComponent } from './page/product-field/product-field-add/product-field-add.component';
import { ProductFieldUpdateComponent } from './page/product-field/product-field-update/product-field-update.component';
import {FormsModule} from'@angular/forms'
import {DomesticShippingListComponent} from './page/shipping-order/domestic/domestic-shipping-list/domestic-shipping-list.component';
import {
  HTTP_INTERCEPTORS,
  HttpClientModule
} from '@angular/common/http';
// import { provideRouter, withHashLocation } from '@angular/router';
import { TokenInterceptor } from './auth-service/interceptor/token.interceptor';

//primeng imports
import { InputTextModule } from 'primeng/inputtext';
import { DropdownModule } from 'primeng/dropdown';
import { ToolbarModule } from 'primeng/toolbar';
import { ButtonModule } from 'primeng/button';
import { TableModule } from 'primeng/table';
import { BreadcrumbModule } from 'primeng/breadcrumb';
import { MultiSelectModule } from 'primeng/multiselect';
import { DomesticShippingOrderHistoryComponent } from './page/shipping-order/domestic/domestic-shipping-order-history/domestic-shipping-order-history.component';
import { AddDomesticShippingComponent } from './page/shipping-order/domestic/add-domestic-shipping/add-domestic-shipping.component';
import { UpdateDomesticShippingComponent } from './page/shipping-order/domestic/update-domestic-shipping/update-domestic-shipping.component';
import { TileComponent } from './page/shipping-order/international/tile/tile.component';
import { InternationalShippingListComponent } from './page/shipping-order/international/international-shipping-list-road/international-shipping-list.component';
import { InternationalShippingOrderHistoryComponent } from './page/shipping-order/international/international-shipping-order-history-by-road/international-shipping-order-history.component';


@NgModule({
  declarations: [
    AppComponent,
    LoginFormComponent,
    DashboardHeadComponent,
    DashboardComponent,
    SidebarComponent,
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
    StatusListComponent,
    AddStatusComponent,
    UpdateStatusComponent,
    VehicleTypeListComponent,
    AddVehicleTypeComponent,
    UpdateVehicleTypeComponent,
    ProductFieldListComponent,
    ProductFieldAddComponent,
    ProductFieldUpdateComponent,
    PermissionListComponent,
    AddPermissionComponent,
    EditPermissionComponent,
    RoleListComponent,
    AddRoleComponent,
    EditRoleComponent,
    DomesticShippingListComponent,
    DomesticShippingOrderHistoryComponent,
    AddDomesticShippingComponent,
    UpdateDomesticShippingComponent,
    TileComponent,
    InternationalShippingListComponent,
    InternationalShippingOrderHistoryComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    CommonModule,
    FormsModule, 
    HttpClientModule,
    InputTextModule,
    DropdownModule,
    ToolbarModule,
    ButtonModule,
    TableModule,
    BreadcrumbModule,
    MultiSelectModule
  ],

  providers: [
    // AuthGuard,
    // DatePipe,
    { provide: HTTP_INTERCEPTORS, useClass: TokenInterceptor, multi: true },
    // { provide: HTTP_INTERCEPTORS, useClass: LoaderInterceptor, multi: true }, // Register the AuthInterceptor
    // provideRouter(routes, withHashLocation()),
],
  bootstrap: [AppComponent]
})
export class AppModule { }
