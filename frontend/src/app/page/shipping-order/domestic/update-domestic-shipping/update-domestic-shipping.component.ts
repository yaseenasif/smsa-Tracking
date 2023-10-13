import { Component } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MenuItem } from 'primeng/api';

@Component({
  selector: 'app-update-domestic-shipping',
  templateUrl: './update-domestic-shipping.component.html',
  styleUrls: ['./update-domestic-shipping.component.scss']
})
export class UpdateDomesticShippingComponent {
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
    this.items = [{ label: 'Domestic Shipment',routerLink:'/domestic-shipping'},{ label: 'Edit Domestic Shipment'}];
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