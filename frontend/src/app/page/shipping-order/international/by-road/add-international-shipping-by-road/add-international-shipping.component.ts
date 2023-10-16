import { Component } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MenuItem } from 'primeng/api';

@Component({
  selector: 'app-add-international-shipping',
  templateUrl: './add-international-shipping.component.html',
  styleUrls: ['./add-international-shipping.component.scss']
})
export class AddInternationalShippingComponent {
  items: MenuItem[] | undefined;
  location!:Location[];
  selectedLocation!:Location;

  constructor() { }
  name!:string;
  checked!:boolean;
  size=100000
  uploadedFiles: any[] = [];
  onUpload(event: any) {
    
  }
  onUpload1(event:any) {
    for(let file of event.files) {
        this.uploadedFiles.push(file);
    }
  }
  
  ngOnInit(): void {
    this.items = [{ label: 'International Shipment',routerLink:'/international-tile'},{ label: 'International Shipment By Road',routerLink:'/international-shipment-by-road'},{ label: 'Add International Shipment By Road'}];
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

