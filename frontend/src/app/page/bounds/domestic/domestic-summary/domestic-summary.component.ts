import { Component } from '@angular/core';
import { MenuItem, MessageService } from 'primeng/api';
import { SummaryService } from '../../service/summary.service';
import { SummarySearch } from '../../../../model/summarySearch';
import { DatePipe } from '@angular/common';
import { ShipmentStatusService } from '../../../shipment-status/service/shipment-status.service';
import { ShipmentStatus } from 'src/app/model/ShipmentStatus';
import { AuthguardService } from 'src/app/auth-service/authguard/authguard.service';
import { ProductField } from 'src/app/model/ProductField';
import { ProductFieldServiceService } from 'src/app/page/product-field/service/product-field-service.service';
import { DomesticShippingService } from 'src/app/page/shipping-order/domestic/service/domestic-shipping.service';

@Component({
  selector: 'app-domestic-summary',
  templateUrl: './domestic-summary.component.html',
  styleUrls: ['./domestic-summary.component.scss'],
  providers: [MessageService, DatePipe]
})
export class DomesticSummaryComponent {
  name!: string;
  bound!: Bound[];
  // shipmentStatus!: ShipmentStatus[];
  shipmentStatus!: ProductField | null | undefined;
  role: any;

  selectedBound: Bound = {
    bound: "All"
  };
  page: number = 0;
  size: number = 10;
  paginationRes: any;

  constructor(
    private summaryService: SummaryService,
    private datePipe: DatePipe,
    private messageService: MessageService,
    private authguardService: AuthguardService,
    // private shipmentStatusService: ShipmentStatusService,
    private shipmentStatusService: ProductFieldServiceService,
    private domesticShippingService:DomesticShippingService
  ) { }
  domesticShipment: any = [];
  items: MenuItem[] | undefined;

  search: any = {
    fromDate: "",
    toDate: "",
    status: "",
    origin: "",
    destinations: [],
    routeNumber:""
  }

  onBoundChange() {
  // this.clearSearch();
  // this.domesticShipment=[];
  
    // if (this.selectedBound && this.selectedBound.bound === "In bound") {
    //   this.getInboundSummary(this.search, 0, 10);
    // } else if (this.selectedBound && this.selectedBound.bound === "Out bound") {
    //   this.getOutboundSummary(this.search, 0, 10);
    // }else{
    //   this.getAllShipmentDomestic(this.search, 0, 10)
    // }
  }

   clearSearch(){
    this.search={
      fromDate: "",
      toDate: "",
      status: "",
      origin: "",
      destinations: [],
      routeNumber:""
    }
   }


  ngOnInit() {
    this.getRole()
    this.getAllShipmentStatus();
    // this.getAllShipmentDomestic(this.search, 0, 10);
    //new
    this.getInboundSummary(this.search, 0, 10);
    this.items = [{ label: 'Domestic Summary' }];
    // this.bound = [
    //   {
    //     bound: "All"
    //   },
    //   {
    //     bound: "In bound"
    //   },
    //   {
    //     bound: "Out bound"
    //   }
    // ]
  }

  getRole() {
    const token = localStorage.getItem('accessToken');

    const decodeToken = this.authguardService.getDecodedAccessToken(token!);

    this.role = decodeToken.ROLES;
    console.log(this.role);

  }

  getInboundSummary(obj: SummarySearch, page: number, size: number) {
    this.domesticShipment=[]

    this.summaryService.getInboundSummary(obj, page, size).subscribe((res: any) => {

      this.domesticShipment = res.content;
      this.paginationRes = res;
      this.search.fromDate= this.search.fromDate ? new Date( this.search.fromDate) : "";
      this.search.toDate= this.search.toDate ? new Date( this.search.toDate) : "";
    }, (error: any) => {
      this.search.fromDate= this.search.fromDate ? new Date( this.search.fromDate) : "";
      this.search.toDate= this.search.toDate ? new Date( this.search.toDate) : "";
      if (error.error.body) {
        this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
      } else {
        this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error });
      }
    })
  }

  getOutboundSummary(obj: SummarySearch, page: number, size: number) {
    this.summaryService.getOutboundSummary(obj, page, size).subscribe((res: any) => {
      this.domesticShipment = res.content;
      this.paginationRes = res;
      this.search.fromDate= this.search.fromDate ? new Date( this.search.fromDate) : "";
      this.search.toDate= this.search.toDate ? new Date( this.search.toDate) : "";
    }, (error: any) => {
      this.search.fromDate= this.search.fromDate ? new Date( this.search.fromDate) : "";
      this.search.toDate= this.search.toDate ? new Date( this.search.toDate) : "";
      if (error.error.body) {
        this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
      } else {
        this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error });
      }
    })
  }

  getAllShipmentStatus() {
    this.shipmentStatusService.getProductFieldByName("Search_For_Domestic").subscribe((res: ProductField) => {
      this.shipmentStatus = res;
    }, error => {
      this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
    })
  }
  getAllShipmentDomestic(obj: SummarySearch, page: number, size: number) {
    let objForAll={
      fromDate: obj.fromDate,
      toDate: obj.toDate,
      status: obj.status,
      origin: obj.origin,
      destination: obj.destination,
      routeNumber:obj.routeNumber,
      user:{},
      activeStatus:true
    }
    this.domesticShippingService.getALLShipments(objForAll, page, size).subscribe((res: any) => {
      
      this.domesticShipment = res.content;
      this.paginationRes = res;
      this.search.fromDate= this.search.fromDate ? new Date( this.search.fromDate) : "";
      this.search.toDate= this.search.toDate ? new Date( this.search.toDate) : "";
    }, (error: any) => {
      this.search.fromDate= this.search.fromDate ? new Date( this.search.fromDate) : "";
      this.search.toDate= this.search.toDate ? new Date( this.search.toDate) : "";
      if (error.error.body) {
        this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
      } else {
        this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error });
      }
    })
  }
  onSubmit() {
    this.domesticShipment=[]
    this.search.fromDate=this.datePipe.transform(this.search.fromDate, 'yyyy-MM-dd')!=null?(this.datePipe.transform(this.search.fromDate, 'yyyy-MM-dd'))!:"";
    this.search.toDate=this.datePipe.transform(this.search.toDate, 'yyyy-MM-dd')!=null?(this.datePipe.transform(this.search.toDate, 'yyyy-MM-dd'))!:"";
   //new
    this.getInboundSummary(this.search, 0, 10);
    // if (this.selectedBound.bound === "In bound") {
    //   this.getInboundSummary(this.search, 0, 10);
    // }
    // else if(this.selectedBound.bound === "Out bound"){
    //   this.getOutboundSummary(this.search, 0, 10);
    // }else{
    //   this.getAllShipmentDomestic(this.search, 0, 10)
    // }
  }

  onPageChange(event: any) {
    this.page = event.page;
    this.size = event.rows;
    //new
    this.getInboundSummary(this.search, this.page, this.size);
    // if (this.selectedBound.bound === "In bound") {
    // this.getInboundSummary(this.search, this.page, this.size);
    // }
    // else if(this.selectedBound.bound === "Out bound"){
    // this.getOutboundSummary(this.search, this.page, this.size)
    // }
    // else{
    //   this.getAllShipmentDomestic(this.search,this.page, this.size)
    // }
  }
}

interface Bound {
  bound: string
}


