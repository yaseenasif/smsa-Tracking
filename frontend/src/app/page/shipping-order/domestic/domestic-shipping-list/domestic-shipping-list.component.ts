import { Component, OnInit } from '@angular/core';
import { MenuItem, MessageService } from 'primeng/api';
import { DomesticShippingService } from '../service/domestic-shipping.service';
import { DomesticShipment } from '../../../../model/DomesticShipment';


@Component({
  selector: 'app-domestic-shipping-list',
  templateUrl: './domestic-shipping-list.component.html',
  styleUrls: ['./domestic-shipping-list.component.scss'],
  providers: [MessageService]
})
export class DomesticShippingListComponent implements OnInit {

  myApiResponse: any;

  constructor(private domesticShipmentService: DomesticShippingService,
    private messageService: MessageService,
  ) { }
  domesticShipment: DomesticShipment[] = []
  items: MenuItem[] | undefined;


  ngOnInit() {
    this.items = [{ label: 'Domestic Shipment' }];
    this.getAllDomesticShipments(this.fromDate,this.toDate,this.status,this.origin,this.destination,this.routeNumber, undefined, undefined);
  }
  page = 0;
  size = 10;
  first: number = 0;

  fromDate: string = '';
  toDate: string = '';
  status: string = '';
  origin: string = '';
  destination: string = '';
  routeNumber: string = '';

  rows: number = 10;

  totalRecords: number = 0;

  getAllDomesticShipments(fromDate?: string,toDate?: string,status?: string,origin?: string,destination?: string,routeNumber?: string, page?: number, size?: number) {
    this.domesticShipmentService.getALLShipments({ fromDate: fromDate,toDate: toDate,status: status,origin: origin,destination: destination,routeNumber: routeNumber, user: {} }, page, size).subscribe((res: any) => {
      this.myApiResponse = res;
      this.page=res.pageable.pageNumber;
      this.size=res.size;
      this.totalRecords = this.myApiResponse.totalElements;
      this.domesticShipment = this.myApiResponse.content;
    }, (error: any) => {
      if (error.error.body) {
        this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
      } else {
        this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error });
      }
    })
  }

  deleteDomesticShipment(id: number) {
    this.domesticShipmentService.deleteDomesticShipment(id).subscribe((res: any) => {
      this.messageService.add({ severity: 'success', summary: 'Success', detail: res.message });

      this.getAllDomesticShipments();

    }, (error: any) => {

      if (error.error.body) {
        this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
      } else {
        this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error });
      }
    })
  }




  onPageChange(event: any) {
   
    this.page = event.page;
    this.rows = event.rows;
    this.getAllDomesticShipments(this.fromDate,this.toDate,this.status,this.origin,this.destination,this.routeNumber, this.page, this.rows);
  }

  searchByFilter(){
    this.getAllDomesticShipments(this.fromDate,this.toDate,this.status,this.origin,this.destination,this.routeNumber, undefined, undefined);
  }
}
