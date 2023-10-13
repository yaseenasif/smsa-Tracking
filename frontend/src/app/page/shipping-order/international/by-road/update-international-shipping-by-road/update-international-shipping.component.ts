import { Component } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MenuItem } from 'primeng/api';

@Component({
  selector: 'app-update-international-shipping',
  templateUrl: './update-international-shipping.component.html',
  styleUrls: ['./update-international-shipping.component.scss']
})
export class UpdateInternationalShippingComponent {
  items: MenuItem[] | undefined;
  location!:Location[];
  selectedLocation!:Location;

  constructor() { }
  name!:string;
  checked!:boolean;
   size=100000
  onUpload(event: any) {
    
  }
  
  ngOnInit(): void {
    this.items = [{ label: 'International Shipment',routerLink:'/international-tile'},{ label: 'International Shipment By Road',routerLink:'/international-shipment-by-road'},{ label: 'Edit International Shipment By Road'}];
    this.location=[
      {
        locationName:"karachi",
        id:1
      },
      {
        locationName:"kaAAi",
        id:2
      },
      {
        locationName:"Alld",
        id:3
      },
      {
        locationName:"islamabad",
        id:4
      },
      {
        locationName:"lahore",
        id:5
      },
    ]
  }
}

interface Location{
  locationName:string,
  id:number
}


