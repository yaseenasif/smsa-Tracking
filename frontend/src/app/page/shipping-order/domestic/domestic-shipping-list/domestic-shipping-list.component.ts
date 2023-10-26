import { Component, OnInit } from '@angular/core';
import { MenuItem } from 'primeng/api';
import { DomesticShippingService } from '../service/domestic-shipping.service';
import { DomesticShipment } from 'src/app/model/DomesticShipment';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-domestic-shipping-list',
  templateUrl: './domestic-shipping-list.component.html',
  styleUrls: ['./domestic-shipping-list.component.scss']
})
export class DomesticShippingListComponent implements OnInit{

  myApiResponse:any;

  constructor(private domesticShipmentService:DomesticShippingService,
    ) { }
  domesticShipment:DomesticShipment[]=[]
  items: MenuItem[] | undefined;
 

  ngOnInit() {
      this.items = [{ label: 'Domestic Shipment'}];
      this.getAllDomesticShipments();
  }
  page=0;
  size=10;

  getAllDomesticShipments(){
    this.domesticShipmentService.getALLShipments({value:"",user:{}},this.page,this.size).subscribe((res:any)=>{
      this.myApiResponse=res;
      this.domesticShipment=this.myApiResponse.content;
    },(error:any)=>{
      console.log("some error occurred");
    })
  }

  deleteDomesticShipment(id:number){
    debugger
    this.domesticShipmentService.deleteDomesticShipment(id).subscribe((res:any)=>{
    debugger

      console.log(res);
      this.getAllDomesticShipments();
      
    },(error:any)=>{
    debugger

      console.log(error);
      
    })
  }
}
