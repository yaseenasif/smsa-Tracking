import { Component } from '@angular/core';
import { MenuItem } from 'primeng/api';

@Component({
  selector: 'app-international-shipping-order-history',
  templateUrl: './international-shipping-order-history.component.html',
  styleUrls: ['./international-shipping-order-history.component.scss']
})
export class InternationalShippingOrderHistoryComponent {
  constructor() { }
  domesticShipmentHistory:any=[{status:"Cleared",processTime:"10/16/2022 15:45",locationCode:"KSA GW",user:"9590",remarks:"Load Cleared and Forward"},
  {status:"Cleared",processTime:"10/16/2022 15:45",locationCode:"KSA GW",user:"9590",remarks:"Load Cleared and Forward"},
  {status:"Cleared",processTime:"10/16/2022 15:45",locationCode:"KSA GW",user:"9590",remarks:"Load Cleared and Forward"},
  {status:"Cleared",processTime:"10/16/2022 15:45",locationCode:"KSA GW",user:"9590",remarks:"Load Cleared and Forward"},
  {status:"Cleared",processTime:"10/16/2022 15:45",locationCode:"KSA GW",user:"9590",remarks:"Load Cleared and Forward"},
  {status:"Cleared",processTime:"10/16/2022 15:45",locationCode:"KSA GW",user:"9590",remarks:"Load Cleared and Forward"},
  {status:"Cleared",processTime:"10/16/2022 15:45",locationCode:"KSA GW",user:"9590",remarks:"Load Cleared and Forward"},
  {status:"Cleared",processTime:"10/16/2022 15:45",locationCode:"KSA GW",user:"9590",remarks:"Load Cleared and Forward"},
  {status:"Cleared",processTime:"10/16/2022 15:45",locationCode:"KSA GW",user:"9590",remarks:"Load Cleared and Forward"},
  {status:"Cleared",processTime:"10/16/2022 15:45",locationCode:"KSA GW",user:"9590",remarks:"Load Cleared and Forward"},];
  items: MenuItem[] | undefined;


  ngOnInit() {
      this.items = [{ label: 'International Shipment',routerLink:'/international-tile'},{ label: 'International Shipment By Road',routerLink:'/international-shipment-by-road'},{ label: 'International Shipment History By Road'}];
  }
}
