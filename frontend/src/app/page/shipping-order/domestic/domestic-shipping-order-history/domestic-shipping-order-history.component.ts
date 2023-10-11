import { Component } from '@angular/core';
import { MenuItem } from 'primeng/api';

@Component({
  selector: 'app-domestic-shipping-order-history',
  templateUrl: './domestic-shipping-order-history.component.html',
  styleUrls: ['./domestic-shipping-order-history.component.scss']
})
export class DomesticShippingOrderHistoryComponent {
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
      this.items = [{ label: 'Domestic Shipment',routerLink:'/domestic-shipping'},{ label: 'Domestic Shipping History'}];
  }

}

