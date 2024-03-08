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
import {FormsModule} from'@angular/forms'
import {DomesticShippingListComponent} from './page/shipping-order/domestic/domestic-shipping-list/domestic-shipping-list.component';
import {
  HTTP_INTERCEPTORS,
  HttpClientModule
} from '@angular/common/http';
// import { provideRouter, withHashLocation } from '@angular/router';
import { TokenInterceptor } from './auth-service/interceptor/token.interceptor';
import { CalendarModule } from 'primeng/calendar';
import { InputNumberModule } from 'primeng/inputnumber';
import { PaginatorModule } from 'primeng/paginator';
import { BadgeModule } from 'primeng/badge';
import { HashLocationStrategy, LocationStrategy } from '@angular/common';

//primeng imports
import { CheckboxModule } from 'primeng/checkbox';
import { ChartModule } from 'primeng/chart';
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
import { TileComponent } from './page/shipping-order/international/by-road/tile/tile.component';
import { InternationalShippingListComponent } from './page/shipping-order/international/by-road/international-shipping-list-road/international-shipping-list.component';
import { InternationalShippingOrderHistoryComponent } from './page/shipping-order/international/by-road/international-shipping-order-history-by-road/international-shipping-order-history.component';
import { AddInternationalShippingComponent } from './page/shipping-order/international/by-road/add-international-shipping-by-road/add-international-shipping.component';
import { UpdateInternationalShippingComponent } from './page/shipping-order/international/by-road/update-international-shipping-by-road/update-international-shipping.component';
import { PasswordModule } from 'primeng/password';
import { AddInternationalShipmentByRoadComponent } from './page/shipping-order/international/by-air/add-international-shipment-by-air/add-international-shipment-by-road.component';
import { InternationalShipmentListAirComponent } from './page/shipping-order/international/by-air/international-shipment-list-air/international-shipment-list-air.component';
import { InternationalShipmentOrderHistoryByAirComponent } from './page/shipping-order/international/by-air/international-shipment-order-history-by-air/international-shipment-order-history-by-air.component';
import { UpdateInternationalShipmentByAirComponent } from './page/shipping-order/international/by-air/update-international-shipment-by-air/update-international-shipment-by-air.component';
import { InputSwitchModule } from 'primeng/inputswitch';
import { DomesticSummaryComponent } from './page/bounds/domestic/domestic-summary/domestic-summary.component';
import { InternationalSummaryByRoadComponent } from './page/bounds/international/by-road/international-summary-by-road/international-summary-by-road.component';
import { InternationalSummaryByAirComponent } from './page/bounds/international/by-air/international-summary-by-air/international-summary-by-air.component';
import { FileUploadModule } from 'primeng/fileupload';
import { ToastModule } from 'primeng/toast';
import { NgxUiLoaderModule,NgxUiLoaderHttpModule} from "ngx-ui-loader";
import { DialogModule } from 'primeng/dialog';
import { AddShipmentStatusComponent } from './page/shipment-status/add-shipment-status/add-shipment-status.component';
import { UpdateShipmentStatusComponent } from './page/shipment-status/update-shipment-status/update-shipment-status.component';
import { ShipmentStatusListComponent } from './page/shipment-status/shipment-status-list/shipment-status-list.component';
import { UpdateDomesticShipmentForSummaryComponent } from './page/bounds/domestic/update-domestic-shipment-for-summary/update-domestic-shipment-for-summary.component';
import { AttachmentsComponent } from './page/bounds/domestic/attachments/attachments.component';
import { DomesticAttachmentsComponent } from './page/shipping-order/domestic/domestic-attachments/domestic-attachments.component';
import { UnauthorizedPageComponent } from './page/unauthorized/unauthorized-page/unauthorized-page.component';
import { ViewAttachmentsComponent } from './page/shipping-order/view-attachments/view-attachments.component';
import { UpdateInternationalAirForSummaryComponent } from './page/bounds/international/update-international-air-for-summary/update-international-air-for-summary.component';
import { UpdateInternationalRoadForSummaryComponent } from './page/bounds/international/update-international-road-for-summary/update-international-road-for-summary.component';
import { AddAttachmentsOfInternationalShipmentByAirComponent } from './page/shipping-order/international/by-air/add-attachments-of-international-shipment-by-air/add-attachments-of-international-shipment-by-air.component';
import { AddAttachmentsOfInternationalShipmentByRoadComponent } from './page/shipping-order/international/by-road/add-attachments-of-international-shipment-by-road/add-attachments-of-international-shipment-by-road.component';
import { InternationalSummaryByAirAttachmentsComponent } from './page/bounds/international/by-air/international-summary-by-air-attachments/international-summary-by-air-attachments.component';
import { InternationalSummaryByRoadAttachmentsComponent } from './page/bounds/international/by-road/international-summary-by-road-attachments/international-summary-by-road-attachments.component';
import { ViewShipmentComponent } from './page/shipping-order/domestic/view-shipment/view-shipment.component';
import { ViewShipmentAirComponent } from './page/shipping-order/international/by-air/view-shipment-air/view-shipment-air.component';
import { ViewShipmentRoadComponent } from './page/shipping-order/international/by-road/view-shipment-road/view-shipment-road.component';
import { GetInternationalAirRoutesComponent } from './page/InternationalRoutes/byAir/get-international-air-routes/get-international-air-routes.component';
import { AddInternationalAirRoutesComponent } from './page/InternationalRoutes/byAir/add-international-air-routes/add-international-air-routes.component';
import { UpdateInternationalAirRoutesComponent } from './page/InternationalRoutes/byAir/update-international-air-routes/update-international-air-routes.component';
import { UpdateInternationalRoadRoutesComponent } from './page/InternationalRoutes/byRoad/update-international-road-routes/update-international-road-routes.component';
import { AddInternationalRoadRoutesComponent } from './page/InternationalRoutes/byRoad/add-international-road-routes/add-international-road-routes.component';
import { GetInternationalRoadRoutesComponent } from './page/InternationalRoutes/byRoad/get-international-road-routes/get-international-road-routes.component';
import { GetDomesticRoutesComponent } from './page/domesticRoutes/get-domestic-routes/get-domestic-routes.component';
import { AddDomesticRoutesComponent } from './page/domesticRoutes/add-domestic-routes/add-domestic-routes.component';
import { UpdateDomesticRoutesComponent } from './page/domesticRoutes/update-domestic-routes/update-domestic-routes.component';
import { EmailManagmentComponent } from './page/email-managment/email-managment.component';
import { ChipsModule } from 'primeng/chips';
import { ReportTilesComponent } from './page/report/report-tiles/report-tiles.component';
import { InternationalAirReportPerformanceComponent } from './page/report/international-air-report-performance/international-air-report-performance.component';
import { InternationalAirReportStatusComponent } from './page/report/international-air-report-status/international-air-report-status.component';
import { InternationalRoadReportStatusComponent } from './page/report/international-road-report-status/international-road-report-status.component';
import { InternationalRoadReportPerformanceComponent } from './page/report/international-road-report-performance/international-road-report-performance.component';
import { DomesticReportPerformanceComponent } from './page/report/domestic-report-performance/domestic-report-performance.component';
import { TooltipModule } from 'primeng/tooltip';
import { ViewDomesticShippingForSummaryComponent } from './page/bounds/domestic/view-domestic-shipping-for-summary/view-domestic-shipping-for-summary.component';
import { ViewAirShippingForSummaryComponent } from './page/bounds/international/by-air/view-air-shipping-for-summary/view-air-shipping-for-summary.component';
import { ViewRoadShippingForSummaryComponent } from './page/bounds/international/by-road/view-road-shipping-for-summary/view-road-shipping-for-summary.component';
import { AddCountryComponent } from './page/country/add-country/add-country.component';
import { CountryListComponent } from './page/country/country-list/country-list.component';
import { CountryUpdateComponent } from './page/country/country-update/country-update.component';
import { AddFacilityComponent } from './page/facility/add-facility/add-facility.component';
import { FacilityUpdateComponent } from './page/facility/facility-update/facility-update.component';
import { FacilityListComponent } from './page/facility/facility-list/facility-list.component';
import { ArchiveUserComponent } from './page/user/archive-user/archive-user.component';
import { TwelveDigitValidatorDirective } from './page/shipping-order/twelve-digit-validator.directive';
import { IsNumberDirective } from './page/shipping-order/is-number.directive';
import { SevenDigitValidatorDirective } from './page/shipping-order/seven-digit-validator.directive';
import { AddDomesticEmailListComponent } from './page/domesticRoutes/add-domestic-email-list/add-domestic-email-list.component';
import { AddInternationalByAirEmailListComponent } from './page/InternationalRoutes/byAir/add-international-by-air-email-list/add-international-by-air-email-list.component';
import { AddInternationalByRoadEmailListComponent } from './page/InternationalRoutes/byRoad/add-international-by-road-email-list/add-international-by-road-email-list.component';
import { ChipModule } from 'primeng/chip';
import { ClipboardModule } from 'ngx-clipboard';

//Dashboard
import { MenuModule } from 'primeng/menu';
import { StyleClassModule } from 'primeng/styleclass';
import { PanelMenuModule } from 'primeng/panelmenu';

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
    VehicleTypeListComponent,
    AddVehicleTypeComponent,
    UpdateVehicleTypeComponent,
    ProductFieldListComponent,
    ProductFieldAddComponent,
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
    InternationalShippingOrderHistoryComponent,
    AddInternationalShippingComponent,
    UpdateInternationalShippingComponent,
    AddInternationalShipmentByRoadComponent,
    InternationalShipmentListAirComponent,
    InternationalShipmentOrderHistoryByAirComponent,
    UpdateInternationalShipmentByAirComponent,
    DomesticSummaryComponent,
    InternationalSummaryByRoadComponent,
    InternationalSummaryByAirComponent,
    AddShipmentStatusComponent,
    UpdateShipmentStatusComponent,
    ShipmentStatusListComponent,
    UnauthorizedPageComponent,
    UpdateDomesticShipmentForSummaryComponent,
    AttachmentsComponent,
    DomesticAttachmentsComponent,
    ViewAttachmentsComponent,
    UpdateInternationalAirForSummaryComponent,
    UpdateInternationalRoadForSummaryComponent,
    AddAttachmentsOfInternationalShipmentByAirComponent,
    AddAttachmentsOfInternationalShipmentByRoadComponent,
    InternationalSummaryByAirAttachmentsComponent,
    InternationalSummaryByRoadAttachmentsComponent,
    ViewShipmentComponent,
    ViewShipmentAirComponent,
    ViewShipmentRoadComponent,
    GetInternationalAirRoutesComponent,
    AddInternationalAirRoutesComponent,
    UpdateInternationalAirRoutesComponent,
    UpdateInternationalRoadRoutesComponent,
    AddInternationalRoadRoutesComponent,
    GetInternationalRoadRoutesComponent,
    GetDomesticRoutesComponent,
    AddDomesticRoutesComponent,
    UpdateDomesticRoutesComponent,
    EmailManagmentComponent,
    ReportTilesComponent,
    InternationalAirReportPerformanceComponent,
    InternationalAirReportStatusComponent,
    InternationalRoadReportStatusComponent,
    InternationalRoadReportPerformanceComponent,
    DomesticReportPerformanceComponent,
    ViewDomesticShippingForSummaryComponent,
    ViewAirShippingForSummaryComponent,
    ViewRoadShippingForSummaryComponent,
    AddCountryComponent,
    CountryListComponent,
    CountryUpdateComponent,
    AddFacilityComponent,
    FacilityUpdateComponent,
    FacilityListComponent,
    ArchiveUserComponent,
    TwelveDigitValidatorDirective,
    IsNumberDirective,
    SevenDigitValidatorDirective,
    AddDomesticEmailListComponent,
    AddInternationalByAirEmailListComponent,
    AddInternationalByRoadEmailListComponent,
    
  ],
  imports: [
    PanelMenuModule,
    StyleClassModule,
    MenuModule,
    ClipboardModule,
    ChipModule,
    ChipsModule,
    CheckboxModule,
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
    MultiSelectModule,
    PasswordModule,
    InputSwitchModule,
    CalendarModule,
    FileUploadModule,
    ToastModule,
    ChartModule,
    NgxUiLoaderModule,
    DialogModule,
    InputNumberModule,
    TooltipModule,
    PaginatorModule,
    BadgeModule,
    NgxUiLoaderHttpModule.forRoot({
      showForeground:true
    })
  ],

  providers: [
    // AuthGuard,
    // DatePipe,
    {provide: LocationStrategy, useClass: HashLocationStrategy},
    { provide: HTTP_INTERCEPTORS, useClass: TokenInterceptor, multi: true },
    // { provide: HTTP_INTERCEPTORS, useClass: LoaderInterceptor, multi: true }, // Register the AuthInterceptor
    // provideRouter(routes, withHashLocation()),
],
  bootstrap: [AppComponent]
})
export class AppModule { }
