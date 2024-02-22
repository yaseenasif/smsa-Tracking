import { Component, OnInit } from '@angular/core';
import { MenuItem, MessageService } from 'primeng/api';
import { DomesticShippingService } from '../service/domestic-shipping.service';
import { DomesticShipment } from '../../../../model/DomesticShipment';
import { ShipmentStatusService } from 'src/app/page/shipment-status/service/shipment-status.service';
import { ShipmentStatus } from 'src/app/model/ShipmentStatus';
import { DatePipe } from '@angular/common';
import { ProductField } from 'src/app/model/ProductField';
import { ProductFieldServiceService } from 'src/app/page/product-field/service/product-field-service.service';
import { LocationService } from 'src/app/page/location/service/location.service';
import { Location } from 'src/app/model/Location';
import {  Routes } from 'src/app/model/ShipmentRoutes';
import { DomesticRoutesService } from 'src/app/page/domesticRoutes/service/domestic-routes.service';

@Component({
  selector: 'app-domestic-shipping-list',
  templateUrl: './domestic-shipping-list.component.html',
  styleUrls: ['./domestic-shipping-list.component.scss'],
  providers: [MessageService, DatePipe],
})
export class DomesticShippingListComponent implements OnInit {
  myApiResponse: any;
  // shipmentStatus!: ShipmentStatus[];
  shipmentStatus!: ProductField | null | undefined;
  visible: boolean = false;
  DSid!: number;
  location!: Location[];
  routes!: Routes[];

  constructor(
    private domesticShipmentService: DomesticShippingService,
    private messageService: MessageService,
    private domesticLocation: LocationService,
    private domesticRoutesService: DomesticRoutesService,
    // private shipmentStatusService: ShipmentStatusService,
    private shipmentStatusService: ProductFieldServiceService,
    private datePipe: DatePipe
  ) {}
  domesticShipment: DomesticShipment[] = [];
  items: MenuItem[] | undefined;

  ngOnInit() {
    this.items = [{ label: 'Domestic Outbound' }];
    this.getAllShipmentStatus();
    this.getDomesticLocations();
    this.getAllDomesticRoutesWithoutPagination();
    this.getAllDomesticShipments(
      this.fromDate!,
      this.toDate!,
      this.status,
      this.origin,
      this.destination,
      this.route,
      undefined,
      undefined
    );
  }
  page = 0;
  size = 10;
  first: number = 0;

  fromDate: string | null = null;
  toDate: string | null = null;
  status: string | null = null;
  origin: Location | null = null;
  destination: Location | null = null;
  route: Routes | null = null;

  rows: number = 10;

  totalRecords: number = 0;

  getAllDomesticShipments(
    fromDate?: string,
    toDate?: string,
    status?: string | null,
    origin?: Location | null,
    destination?: Location | null,
    route?: Routes | null,
    page?: number,
    size?: number
  ) {
    this.domesticShipmentService
      .getALLShipments(
        {
          fromDate: this.fromDate
            ? this.datePipe.transform(new Date(this.fromDate), 'yyyy-MM-dd')
            : null,
          toDate: this.toDate
            ? this.datePipe.transform(new Date(this.toDate), 'yyyy-MM-dd')
            : null,
          status: status,
          origin: origin,
          destination: destination,
          route: route,
          user: null,
          activeStatus: true,
        },
        page,
        size
      )
      .subscribe(
        (res: any) => {
          this.myApiResponse = res;
          this.page = res.pageable.pageNumber;
          this.size = res.size;
          this.totalRecords = this.myApiResponse.totalElements;
          this.domesticShipment = this.myApiResponse.content;
        },
        (error: any) => {
          if (error.error.body) {
            this.messageService.add({
              severity: 'error',
              summary: 'Error',
              detail: error.error.body,
            });
          } else {
            this.messageService.add({
              severity: 'error',
              summary: 'Error',
              detail: error.error,
            });
          }
        }
      );
  }

  onPageChange(event: any) {
    this.page = event.page;
    this.rows = event.rows;
    this.getAllDomesticShipments(
      this.fromDate!,
      this.toDate!,
      this.status,
      this.origin,
      this.destination,
      this.route,
      this.page,
      this.rows
    );
  }

  searchByFilter() {
    this.getAllDomesticShipments(
      this.fromDate!,
      this.toDate!,
      this.status,
      this.origin,
      this.destination,
      this.route,
      undefined,
      undefined
    );
  }

  getAllShipmentStatus() {
    this.shipmentStatusService
      .getProductFieldByName('Search_For_Domestic')
      .subscribe(
        (res: ProductField) => {
          this.shipmentStatus = res;
        },
        (error) => {
          this.messageService.add({
            severity: 'error',
            summary: 'Error',
            detail: error.error.body,
          });
        }
      );
  }
  clearFilter() {
    this.fromDate = null;
    this.toDate = null;
    this.status = null;
    this.origin = null;
    this.destination = null;
    this.route = null;
    this.getAllDomesticShipments(
      this.fromDate!,
      this.toDate!,
      this.status,
      this.origin,
      this.destination,
      this.route,
      undefined,
      undefined
    );
  }

  deleteDomesticShipmentByID(id: number) {
    this.domesticShipmentService.deleteDomesticShipment(id).subscribe(
      (res: any) => {
        this.messageService.add({
          severity: 'success',
          summary: 'Success',
          detail: 'shipment is deleted successfully',
        });
        this.getAllDomesticShipments(
          this.fromDate!,
          this.toDate!,
          this.status,
          this.origin,
          this.destination,
          this.route,
          this.page,
          this.size
        );
        this.visible = false;
      },
      (error: any) => {
        if (error.error.body) {
          this.messageService.add({
            severity: 'error',
            summary: 'Error',
            detail: error.error.body,
          });
        } else {
          this.messageService.add({
            severity: 'error',
            summary: 'Error',
            detail: error.error,
          });
        }
        this.visible = false;
      }
    );
  }
  showModal(id: number) {
    this.visible = true;
    this.DSid = id;
  }
  getDomesticLocations() {
    this.domesticLocation.getAllLocationForDomestic().subscribe(
      (res: Location[]) => {
        this.location = res.filter((el) => el.status);
      },
      (error) => {
        if (error.error.body) {
          this.messageService.add({
            severity: 'error',
            summary: 'Error',
            detail: error.error.body,
          });
        } else {
          this.messageService.add({
            severity: 'error',
            summary: 'Error',
            detail: error.error,
          });
        }
      }
    );
  }
  getAllDomesticRoutesWithoutPagination(){
    this.domesticRoutesService.getAllDomesticRoutesWithoutPagination().subscribe((res:Routes[])=>{
    this.routes=res;
    },(error)=>{
      if (error.error.body) {
        this.messageService.add({
          severity: 'error',
          summary: 'Error',
          detail: error.error.body,
        });
      } else {
        this.messageService.add({
          severity: 'error',
          summary: 'Error',
          detail: error.error,
        });
      }
    })
  }
 
}
