import { Component, OnInit } from '@angular/core';
import { MenuItem, MessageService } from 'primeng/api';
import { DomesticShippingService } from '../service/domestic-shipping.service';
import { DomesticShipment } from 'src/app/model/DomesticShipment';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-domestic-shipping-list',
  templateUrl: './domestic-shipping-list.component.html',
  styleUrls: ['./domestic-shipping-list.component.scss'],
  providers:[MessageService]
})
export class DomesticShippingListComponent implements OnInit{

  myApiResponse:any;

  constructor(private domesticShipmentService:DomesticShippingService,
    private messageService: MessageService,
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
      if(error.error.body){
        this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
      }else{
        this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error });
      }   
    })
  }

  deleteDomesticShipment(id:number){
    this.domesticShipmentService.deleteDomesticShipment(id).subscribe((res:any)=>{
      this.messageService.add({ severity: 'success', summary: 'Success', detail: res.message });

      this.getAllDomesticShipments();
      
    },(error:any)=>{
      
      if(error.error.body){
        this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
      }else{
        this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error });
      }  
    })
  }

  
}
