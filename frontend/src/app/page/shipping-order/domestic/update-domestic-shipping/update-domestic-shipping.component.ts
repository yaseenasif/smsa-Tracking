import { Component } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-update-domestic-shipping',
  templateUrl: './update-domestic-shipping.component.html',
  styleUrls: ['./update-domestic-shipping.component.scss']
})
export class UpdateDomesticShippingComponent {
  form!:FormGroup;
  originCountry:string[]=["UAE","KSA","BAH","KWI","OMN","QAT","JOR"]
  destinationCountry:string[]=["UAE","KSA","BAH","KWI","OMN","QAT","JOR"]
  shipmentMode:string[]=["Courier","LTL","FTL"]
  numberOfPallets:number[]=[]
  shippingStatus:string[]=["Departed","Arrived","Cleared","Held","Border Delay","Customers Delay","System Delay"]
  vehicleType:string[]=[
    "Box Trailer",
    "Flat bed Trailer",
    "Curtain Trailer",
    "10 Ton Truck",
    "9 Ton Truck",
    "8 Ton Truck",
    "7 Ton Truck",
    "6 Ton Truck",
    "5 Ton Truck",
    "4 Ton Truck",
    "Hiroof Van"
    ]

  constructor(private formBuilder:FormBuilder) { }

  ngOnInit(): void {

    this.insertNumberOfPallets();

    this.form = this.formBuilder.group({
      'originCountry':['',[]],
      'originPort':['',[]],
      'mode':['',[]],
      'shipmentMode':['',[]],
      'destinationCountry':['',[]],
      'destinationPort':['',[]],
      'departureDate':['',[]],
      'departureTime':['',[]],
      'numberOfShipments':['',[]],
      'arrivalDate':['',[]],
      'arrivalTime':['',[]],
      'actualWeight':['',[]],
      'driverName':['',[]],
      'driverContact':['',[]],
      'referenceNumber':['',[]],
      'vehicleType':['',[]],
      'numberOfPallets':['',[]],
      'numberOfBags':['',[]],
      'vehicleNumber':['',[]],
      'tagNumber':['',[]],
      'sealNumber':['',[]],
      'attachments':['',[]],
      'status':['',[]],
      'remarks':['',[]],
    })
  }

  insertNumberOfPallets(){
    for (let index = 30; 1 <= index; index--) {
      this.numberOfPallets.push(index);
    }
  }
  
  createShippingOrder(){
    console.log(this.form.value);

    this.form.reset()
  }
}
