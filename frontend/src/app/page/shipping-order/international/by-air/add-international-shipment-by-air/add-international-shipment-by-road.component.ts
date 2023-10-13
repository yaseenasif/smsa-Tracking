import { Component } from '@angular/core';
import { MenuItem } from 'primeng/api';

@Component({
  selector: 'app-add-international-shipment-by-road',
  templateUrl: './add-international-shipment-by-road.component.html',
  styleUrls: ['./add-international-shipment-by-road.component.scss']
})
export class AddInternationalShipmentByRoadComponent {
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
    this.items = [{ label: 'International Shipment',routerLink:'/international-tile'},{ label: 'International Shipment By Air',routerLink:'/international-shipment-by-air'},{ label: 'Add International Shipment By Air'}];
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
