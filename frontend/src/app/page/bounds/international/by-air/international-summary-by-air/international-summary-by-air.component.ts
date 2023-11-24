import { Component } from '@angular/core';
import { MenuItem } from 'primeng/api';
import { ShipmentStatus } from 'src/app/model/ShipmentStatus';
import { SummaryService } from '../../../service/summary.service';
import { ShipmentStatusService } from 'src/app/page/shipment-status/service/shipment-status.service';
import { DatePipe } from '@angular/common';
import { InternationalSummarySearch} from 'src/app/model/InternationalSummarySearch'
import { AuthguardService } from 'src/app/auth-service/authguard/authguard.service';
@Component({
  selector: 'app-international-summary-by-air',
  templateUrl: './international-summary-by-air.component.html',
  styleUrls: ['./international-summary-by-air.component.scss'],
  providers: [DatePipe]
})
export class InternationalSummaryByAirComponent {

  name!:string;
  bound!:Bound[];
  shipmentStatus!:ShipmentStatus[];
  role:any;

  selectedBound:Bound={
    bound:"In bound"
  };

  search:any  ={
    fromDate:null,
    toDate:null,
    status:null,
    origin:null,
    destination:null,
    type:null
  }

  constructor(private summaryService:SummaryService,
    private datePipe: DatePipe,
    private authguardService:AuthguardService,
    private shipmentStatusService:ShipmentStatusService) { }

    internationalShipmentByAir:any=[];
    items: MenuItem[] | undefined;

  ngOnInit() {
    this.getRole()

      this.items = [{ label: 'International Summary By Air'}];
      this.bound=[
        {
          bound:"In bound"
        },
        {
          bound:"Out bound"
        }
      ]
      this.getAllShipmentStatus();
      this.getInboundSummary(this.search,0,10);
  }

  getRole(){
    const token = localStorage.getItem('accessToken');

    const decodeToken = this.authguardService.getDecodedAccessToken(token!);

    this.role=decodeToken.ROLES;
    console.log(this.role);

  }

  onBoundChange() {
    this.search  ={
      fromDate:null,
      toDate:null,
      status:null,
      origin:null,
      destination:null,
      type:null
    }
    if (this.selectedBound && this.selectedBound.bound === "In bound") {
      this.getInboundSummary(this.search, 0, 10);
    } else if (this.selectedBound && this.selectedBound.bound === "Out bound") {
      this.getOutboundSummary(this.search, 0, 10);
    }
  }

  getAllShipmentStatus(){
    this.shipmentStatusService.getALLShipmentStatus().subscribe((res:ShipmentStatus[])=>{
      this.shipmentStatus=res;
    },error=>{



    })
   }

   getInboundSummary(obj:InternationalSummarySearch,page:number,size:number){
    this.summaryService.getInboundSummaryForAir(obj,page,size).subscribe((res:any)=>{
      this.internationalShipmentByAir=res.content;
      this.search  ={
        fromDate:null,
        toDate:null,
        status:null,
        origin:null,
        destination:null,
        type:null
      }
    },(error:any)=>{

      this.internationalShipmentByAir=[];


    })
  }

  getOutboundSummary(obj:InternationalSummarySearch,page:number,size:number){
    this.summaryService.getOutboundSummaryForAir(obj,page,size).subscribe((res:any)=>{
      this.internationalShipmentByAir=res.content;
      this.search  ={
        fromDate:null,
        toDate:null,
        status:null,
        origin:null,
        destination:null,
        type:null
      }
    },(error:any)=>{

      this.internationalShipmentByAir=[];


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
    if(this.search.status===null){
      this.search.type="";
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
