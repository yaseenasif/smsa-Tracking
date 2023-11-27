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
    this.getAllDomesticShipments(this.searchItem, undefined, undefined);
  }
  page = 0;
  size = 10;
  first: number = 0;

  searchItem: string = '';

  rows: number = 10;

  totalRecords: number = 0;

  getAllDomesticShipments(value?: string, page?: number, size?: number) {
    this.domesticShipmentService.getALLShipments({ value: value, user: {} }, page, size).subscribe((res: any) => {

      this.myApiResponse = res;
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
    this.first = event.first;
    this.rows = event.rows;

    this.getAllDomesticShipments(undefined, this.first, this.rows);
  }

}
