import { UpdateInternationalAirRoutesComponent } from './page/InternationalRoutes/byAir/update-international-air-routes/update-international-air-routes.component';
import { UpdateInternationalRoadRoutesComponent } from './page/InternationalRoutes/byRoad/update-international-road-routes/update-international-road-routes.component';
import { AddInternationalRoadRoutesComponent } from './page/InternationalRoutes/byRoad/add-international-road-routes/add-international-road-routes.component';
import { GetInternationalRoadRoutesComponent } from './page/InternationalRoutes/byRoad/get-international-road-routes/get-international-road-routes.component';
import { AddInternationalAirRoutesComponent } from './page/InternationalRoutes/byAir/add-international-air-routes/add-international-air-routes.component';
import { GetInternationalAirRoutesComponent } from './page/InternationalRoutes/byAir/get-international-air-routes/get-international-air-routes.component';
import { UpdateDomesticRoutesComponent } from './page/domesticRoutes/update-domestic-routes/update-domestic-routes.component';
import { AddDomesticRoutesComponent } from './page/domesticRoutes/add-domestic-routes/add-domestic-routes.component';
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
import { AddVehicleTypeComponent } from './page/vehicle-type/add-vehicle-type/add-vehicle-type.component';
import { UpdateVehicleTypeComponent } from './page/vehicle-type/update-vehicle-type/update-vehicle-type.component';
import { VehicleTypeListComponent } from './page/vehicle-type/vehicle-type-list/vehicle-type-list.component';
import { ProductFieldListComponent } from './page/product-field/product-field-list/product-field-list.component';
import { ProductFieldAddComponent } from './page/product-field/product-field-add/product-field-add.component';
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
import { InternationalSummaryByAirComponent } from './page/bounds/international/by-air/international-summary-by-air/international-summary-by-air.component';
import { InternationalSummaryByRoadComponent } from './page/bounds/international/by-road/international-summary-by-road/international-summary-by-road.component';
import { ShipmentStatusListComponent } from './page/shipment-status/shipment-status-list/shipment-status-list.component';
import { AddShipmentStatusComponent } from './page/shipment-status/add-shipment-status/add-shipment-status.component';
import { UpdateShipmentStatusComponent } from './page/shipment-status/update-shipment-status/update-shipment-status.component';
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
import { GetDomesticRoutesComponent } from './page/domesticRoutes/get-domestic-routes/get-domestic-routes.component';
import { ReportTilesComponent } from './page/report/report-tiles/report-tiles.component';
import { DomesticReportPerformanceComponent } from './page/report/domestic-report-performance/domestic-report-performance.component';
import { InternationalAirReportPerformanceComponent } from './page/report/international-air-report-performance/international-air-report-performance.component';
import { InternationalAirReportStatusComponent } from './page/report/international-air-report-status/international-air-report-status.component';
import { InternationalRoadReportPerformanceComponent } from './page/report/international-road-report-performance/international-road-report-performance.component';
import { InternationalRoadReportStatusComponent } from './page/report/international-road-report-status/international-road-report-status.component';
import { ViewDomesticShippingForSummaryComponent } from './page/bounds/domestic/view-domestic-shipping-for-summary/view-domestic-shipping-for-summary.component';
import { ViewAirShippingForSummaryComponent } from './page/bounds/international/by-air/view-air-shipping-for-summary/view-air-shipping-for-summary.component';
import { ViewRoadShippingForSummaryComponent } from './page/bounds/international/by-road/view-road-shipping-for-summary/view-road-shipping-for-summary.component';
import { CountryListComponent } from './page/country/country-list/country-list.component';
import { AddCountryComponent } from './page/country/add-country/add-country.component';
import { CountryUpdateComponent } from './page/country/country-update/country-update.component';
import { FacilityListComponent } from './page/facility/facility-list/facility-list.component';
import { AddFacilityComponent } from './page/facility/add-facility/add-facility.component';
import { FacilityUpdateComponent } from './page/facility/facility-update/facility-update.component';
// import { EmailManagmentComponent } from './page/email-managment/email-managment.component';


const routes: Routes = [
  {
    path: '',
    component: DashboardComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'login',
    component: LoginFormComponent,
  },
  {
    path: 'home',
    component: DashboardComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'driver',
    component: DriverListComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'add-driver',
    component: AddDriverComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'edit-driver/:id',
    component: UpdateDriverComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'location',
    component: LocationListComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'add-location',
    component: AddLocationComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'edit-location/:id',
    component: UpdateLocationComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'location-port',
    component: LocationPortListComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'add-location-port',
    component: AddLocationPortComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'edit-location-port/:id',
    component: UpdateLocationPortComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'user',
    component: UserListComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'add-user',
    component: AddUserComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'edit-user/:id',
    component: UpdateUserComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'shipment-status',
    component: ShipmentStatusListComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'add-shipment-status',
    component: AddShipmentStatusComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'edit-shipment-status/:id',
    component: UpdateShipmentStatusComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'vehicle-type',
    component: VehicleTypeListComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'add-vehicle-type',
    component: AddVehicleTypeComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'edit-vehicle-type/:id',
    component: UpdateVehicleTypeComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'unauthorized',
    component: UnauthorizedPageComponent,
  },
  // {
  //   path: 'permission',
  //   component: PermissionListComponent,

  // },
  // {
  //   path: 'add-permission',
  //   component: AddPermissionComponent,

  // },
  // {
  //   path: 'edit-permission/:id',
  //   component: EditPermissionComponent,
  //   canActivate: [AuthGuard]
  // },
  {
    path: 'role',
    component: RoleListComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'add-role',
    component: AddRoleComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'edit-role/:id',
    component: EditRoleComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'domestic-shipping',
    component: DomesticShippingListComponent,
    canActivate: [AuthGuard]
  },
  // {
  //   path: 'domestic-shipping-history/:id',
  //   component: DomesticShippingOrderHistoryComponent,
  //   canActivate: [AuthGuard]
  // },
  {
    path: 'international-shipping-history-by-air/:id',
    component: DomesticShippingOrderHistoryComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'international-shipping-history-by-road/:id',
    component: InternationalShippingOrderHistoryComponent,
    canActivate: [AuthGuard]
  },

  {
    path: 'add-domestic-shipping',
    component: AddDomesticShippingComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'update-domestic-shipping/:id/:type',
    component: UpdateDomesticShippingComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'update-domestic-shipping-for-summary/:id',
    component: UpdateDomesticShipmentForSummaryComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'update-domestic-shipping-for-summary/:id',
    component: UpdateDomesticShipmentForSummaryComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'view-domestic-shipping-for-summary/:id',
    component: ViewDomesticShippingForSummaryComponent,
    canActivate: [AuthGuard]
  },
  
  {
    path: 'update-international-air-shipping-for-summary/:id',
    component: UpdateInternationalAirForSummaryComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'update-international-road-shipping-for-summary/:id',
    component: UpdateInternationalRoadForSummaryComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'add-attachments/:id',
    component: AttachmentsComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'add-domestic-attachments/:id',
    component: DomesticAttachmentsComponent
    ,
    canActivate: [AuthGuard]
  },
  {
    path: 'international-tile',
    component: TileComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'international-shipment-by-road',
    component: InternationalShippingListComponent,
    canActivate: [AuthGuard]
  },
  // {
  //   path: 'international-shipment-history-by-road/:id',
  //   component: InternationalShippingOrderHistoryComponent,
  //   canActivate: [AuthGuard]
  // },
  {
    path: 'add-international-shipment-by-road',
    component: AddInternationalShippingComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'update-international-shipment-by-road/:id/:type',
    component: UpdateInternationalShippingComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'add-international-by-road-attachments/:id',
    component: AddAttachmentsOfInternationalShipmentByRoadComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'view-attachments/:name/:through/:id',
    component: ViewAttachmentsComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'international-shipment-by-air',
    component: InternationalShipmentListAirComponent,
    canActivate: [AuthGuard]
  },
  // {
  //   path: 'international-shipment-history-by-air/:id',
  //   component: InternationalShipmentOrderHistoryByAirComponent,
  //   canActivate: [AuthGuard]
  // },
  {
    path: 'add-international-shipment-by-air',
    component: AddInternationalShipmentByRoadComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'update-international-shipment-by-air/:id/:type',
    component: UpdateInternationalShipmentByAirComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'add-international-by-air-attachments/:id',
    component: AddAttachmentsOfInternationalShipmentByAirComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'domestic-summary',
    component: DomesticSummaryComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'international-summary-by-air',
    component: InternationalSummaryByAirComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'view-air-shipping-for-summary/:id',
    component:ViewAirShippingForSummaryComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'international-summary-by-air-attachment/:id',
    component: InternationalSummaryByAirAttachmentsComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'international-summary-by-road',
    component: InternationalSummaryByRoadComponent,
    canActivate: [AuthGuard]
  },
  {
    path:'view-road-shipping-for-summary/:id',
    component:ViewRoadShippingForSummaryComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'international-summary-by-road-attachment/:id',
    component: InternationalSummaryByRoadAttachmentsComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'view-domestic-shipment/:id',
    component: ViewShipmentComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'view-international-air/:id',
    component: ViewShipmentAirComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'view-international-road/:id',
    component: ViewShipmentRoadComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'domestic-routes',
    component: GetDomesticRoutesComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'add-domestic-routes',
    component: AddDomesticRoutesComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'update-domestic-routes/:id',
    component: UpdateDomesticRoutesComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'international-routes-for-air',
    component: GetInternationalAirRoutesComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'add-international-routes-for-air',
    component: AddInternationalAirRoutesComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'update-international-routes-for-air/:id',
    component: UpdateInternationalAirRoutesComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'international-routes-for-road',
    component: GetInternationalRoadRoutesComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'add-international-routes-for-road',
    component: AddInternationalRoadRoutesComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'update-international-routes-for-road/:id',
    component: UpdateInternationalRoadRoutesComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'productFields',
    component: ProductFieldListComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'add-ProductField',
    component: ProductFieldAddComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'add-ProductField/:id',
    component: ProductFieldAddComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'report-tiles',
    component: ReportTilesComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'domestic-report-performance',
    component: DomesticReportPerformanceComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'international-air-report-performance',
    component:  InternationalAirReportPerformanceComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'international-air-report-status',
    component:  InternationalAirReportStatusComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'international-road-report-performance',
    component:  InternationalRoadReportPerformanceComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'international-road-report-status',
    component:  InternationalRoadReportStatusComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'country-list',
    component:  CountryListComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'add-country',
    component:  AddCountryComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'update-country/:id',
    component:  CountryUpdateComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'facility-list',
    component:  FacilityListComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'add-facility',
    component:  AddFacilityComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'update-facility/:id',
    component:  FacilityUpdateComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'login',
    component: LoginFormComponent
  },
  // {
  //   path: 'emailManagement',
  //   component: EmailManagmentComponent,
  //   canActivate: [AuthGuard]
  // }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {

}
