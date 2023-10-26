import { Component } from '@angular/core';
import { MenuItem } from 'primeng/api';
import { SummaryService } from '../../service/summary.service';
import { SummarySearch } from 'src/app/model/summarySearch';
import { DatePipe } from '@angular/common';
import { ShipmentStatusService } from 'src/app/page/shipment-status/service/shipment-status.service';
import { ShipmentStatus } from 'src/app/model/ShipmentStatus';

@Component({
  selector: 'app-domestic-summary',
  templateUrl: './domestic-summary.component.html',
  styleUrls: ['./domestic-summary.component.scss'],
  providers: [DatePipe]
})
export class DomesticSummaryComponent {
  name!:string;
  bound!:Bound[];
  shipmentStatus!:ShipmentStatus[];

  selectedBound:Bound={
    bound:"In bound"
  };
  fromDate: Date | undefined;
  toDate: Date | undefined;


  constructor(private summaryService:SummaryService,
    private datePipe: DatePipe,
    private shipmentStatusService:ShipmentStatusService) { }
  domesticShipment:any=[];
  items: MenuItem[] | undefined;

  search:any  ={
    fromDate:null,
    toDate:null,
    status:null,
    origin:null,
    destination:null
  }

  onBoundChange() {
    this.search  ={
      fromDate:null,
      toDate:null,
      status:null,
      origin:null,
      destination:null
    }
    if (this.selectedBound && this.selectedBound.bound === "In bound") {
      this.getInboundSummary(this.search, 0, 10);
    } else if (this.selectedBound && this.selectedBound.bound === "Out bound") {
      this.getOutboundSummary(this.search, 0, 10);
    }
  }
 

  ngOnInit() {
    this.getAllShipmentStatus();
    this.getInboundSummary(this.search,0,10);
      this.items = [{ label: 'Domestic Summary'}];
      this.bound=[
        {
          bound:"In bound"
        },
        {
          bound:"Out bound"
        }
      ]
  }

  getInboundSummary(obj:SummarySearch,page:number,size:number){
    this.summaryService.getInboundSummary(obj,page,size).subscribe((res:any)=>{
      this.domesticShipment=res.content;
      this.search  ={
        fromDate:null,
        toDate:null,
        status:null,
        origin:null,
        destination:null
      }
    },(error:any)=>{
      debugger
      this.domesticShipment=[];
      console.log(error.error.body);
      
    })
  }

  getOutboundSummary(obj:SummarySearch,page:number,size:number){
    this.summaryService.getOutboundSummary(obj,page,size).subscribe((res:any)=>{
      this.domesticShipment=res.content;
      this.search  ={
        fromDate:null,
        toDate:null,
        status:null,
        origin:null,
        destination:null
      }
    },(error:any)=>{
      debugger
      this.domesticShipment=[];
      console.log(error.error.body);
      
    })
  }

  getAllShipmentStatus(){
    this.shipmentStatusService.getALLShipmentStatus().subscribe((res:ShipmentStatus[])=>{
      this.shipmentStatus=res; 
    },error=>{
      
      console.log(error.error.body);

    })
   }
  onSubmit(){
    if(this.search.destination===null){
      this.search.destination="";
    }
    if(this.search.origin===null){
      this.search.origin="";
    }
    if(this.search.status===null){
      this.search.status="";
    }
    if(this.search.toDate===null){
      this.search.toDate="";
    }
    else{
      let originalDate = new Date(this.search.toDate);
      this.search.toDate = this.datePipe.transform(originalDate, 'yyyy-MM-dd');
    }
    if(this.search.fromDate===null){
      this.search.fromDate="";
    }
    else{
      let originalDate = new Date(this.search.fromDate);
      this.search.fromDate = this.datePipe.transform(originalDate, 'yyyy-MM-dd');
    }
    console.log(this.search);
    debugger
    if(this.selectedBound.bound === "In bound"){
      this.getInboundSummary(this.search,0,10);
    }else{
      this.getOutboundSummary(this.search,0,10);

    }
  }

}

interface Bound{
  bound:string
}


