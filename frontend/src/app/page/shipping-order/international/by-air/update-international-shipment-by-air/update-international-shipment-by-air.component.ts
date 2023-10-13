import { Component } from '@angular/core';
import { MenuItem } from 'primeng/api';

@Component({
  selector: 'app-update-international-shipment-by-air',
  templateUrl: './update-international-shipment-by-air.component.html',
  styleUrls: ['./update-international-shipment-by-air.component.scss']
})
export class UpdateInternationalShipmentByAirComponent {
  items: MenuItem[] | undefined;
  location!:Location[];
  selectedLocation!:Location;
  size=100000
  onUpload(event: any) {
    
  }

  constructor() { }
  name!:string;
  checked!:boolean;
  
  ngOnInit(): void {
    this.items = [{ label: 'International Shipment',routerLink:'/international-tile'},{ label: 'International Shipment By Air',routerLink:'/international-shipment-by-air'},{ label: 'Edit International Shipment By Air'}];
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

