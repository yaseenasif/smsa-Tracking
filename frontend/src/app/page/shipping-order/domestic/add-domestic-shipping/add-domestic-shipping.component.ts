import { Component } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MenuItem } from 'primeng/api';

@Component({
  selector: 'app-add-domestic-shipping',
  templateUrl: './add-domestic-shipping.component.html',
  styleUrls: ['./add-domestic-shipping.component.scss']
})
export class AddDomesticShippingComponent {
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

    
    this.items = [{ label: 'Domestic Shipment',routerLink:'/domestic-shipping'},{ label: 'Add Domestic Shipment'}];
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


