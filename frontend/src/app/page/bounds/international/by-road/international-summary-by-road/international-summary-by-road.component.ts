import { Component } from '@angular/core';
import { MenuItem, MessageService } from 'primeng/api';
import { SummaryService } from '../../../service/summary.service';
import { DatePipe } from '@angular/common';
import { ShipmentStatusService } from 'src/app/page/shipment-status/service/shipment-status.service';
import { ShipmentStatus } from 'src/app/model/ShipmentStatus';
import { InternationalSummarySearch } from 'src/app/model/InternationalSummarySearch';
import { AuthguardService } from 'src/app/auth-service/authguard/authguard.service';
import { ProductField } from 'src/app/model/ProductField';
import { ProductFieldServiceService } from 'src/app/page/product-field/service/product-field-service.service';
import { InternationalShippingService } from 'src/app/page/shipping-order/international/service/international-shipping.service';

@Component({
  selector: 'app-international-summary-by-road',
  templateUrl: './international-summary-by-road.component.html',
  styleUrls: ['./international-summary-by-road.component.scss'],
  providers: [DatePipe, MessageService]
})
export class InternationalSummaryByRoadComponent {

  name!: string;
  bound!: Bound[];
  // shipmentStatus!: ShipmentStatus[];
  shipmentStatus!: ProductField | null | undefined;
  role: any;

  selectedBound: Bound = {
    bound: "All"
  };

  search: any = {
    fromDate: "",
    toDate: "",
    status: "",
    origin: "",
    destination: "",
    type: "",
    routeNumber:""
  }
  paginationRes: any;
  page: any;
  size: any;

  constructor(
    private summaryService: SummaryService,
    private messageService: MessageService,
    private datePipe: DatePipe,
    private authguardService: AuthguardService,
    private shipmentStatusService: ProductFieldServiceService,
    private internationalShippingService:InternationalShippingService
    ) { }

  internationalShipmentByRoad: any = [];
  items: MenuItem[] | undefined;

  ngOnInit() {
    this.getRole()

    this.items = [{ label: 'International Summary By Road' }];
    this.bound = [
      {
        bound: "In bound"
      },
      {
        bound: "Out bound"
      },
      {
        bound: "All"
      }
    ]
    this.getAllShipmentStatus();
    this.getAllInternationalShipmentByRoad(this.search, 0, 10);
  }
  getRole() {
    const token = localStorage.getItem('accessToken');

    const decodeToken = this.authguardService.getDecodedAccessToken(token!);

    this.role = decodeToken.ROLES;
    console.log(this.role);

  }

  onBoundChange() {
    this.internationalShipmentByRoad=[];
  this.clear();
    if (this.selectedBound && this.selectedBound.bound === "In bound") {
      this.getInboundSummary(this.search, 0, 10);
    } else if (this.selectedBound && this.selectedBound.bound === "Out bound") {
      this.getOutboundSummary(this.search, 0, 10);
    }else{
      this.getAllInternationalShipmentByRoad(this.search, 0, 10);
    }
  }
  clear(){
    this.search = {
      fromDate: "",
      toDate: "",
      status: "",
      origin: "",
      destination: "",
      type: "",
      routeNumber:""
    }
  }

  getAllShipmentStatus() {
    this.shipmentStatusService.getProductFieldByName("Search_For_International_By_Road").subscribe((res: ProductField) => {
      this.shipmentStatus = res;
    }, error => {
      this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
    })
  }

  getInboundSummary(obj: InternationalSummarySearch, page: number, size: number) {
    this.summaryService.getInboundSummaryForRoad(obj, page, size).subscribe((res: any) => {
      this.internationalShipmentByRoad = res.content;
      this.paginationRes = res;
      this.search.fromDate= this.search.fromDate ? new Date( this.search.fromDate) : "";
      this.search.toDate= this.search.toDate ? new Date( this.search.toDate) : "";
    }, (error: any) => {
      this.search.fromDate= this.search.fromDate ? new Date( this.search.fromDate) : "";
      this.search.toDate= this.search.toDate ? new Date( this.search.toDate) : "";
    
      this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });

    })
  }

  getOutboundSummary(obj: InternationalSummarySearch, page: number, size: number) {
    this.summaryService.getOutboundSummaryForRoad(obj, page, size).subscribe((res: any) => {
      this.internationalShipmentByRoad = res.content;
      this.paginationRes = res;
      this.search.fromDate= this.search.fromDate ? new Date( this.search.fromDate) : "";
      this.search.toDate= this.search.toDate ? new Date( this.search.toDate) : "";
    }, (error: any) => {
      this.search.fromDate= this.search.fromDate ? new Date( this.search.fromDate) : "";
      this.search.toDate= this.search.toDate ? new Date( this.search.toDate) : "";
   
      this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });

    })
  }

  getAllInternationalShipmentByRoad(obj: InternationalSummarySearch, page?: number, size?: number) {
    let objForAll={
      fromDate: obj.fromDate,
      toDate: obj.toDate,
      status: obj.status,
      origin: obj.origin,
      destination: obj.destination,
      routeNumber:"",
      user: {} ,
      type:"",
      activeStatus:true
    }
    this.internationalShippingService.getAllInternationalShipmentByRoad(objForAll, this.page , this.size).subscribe((res: any) => {
      this.internationalShipmentByRoad = res.content;
      this.paginationRes = res;
      this.search.fromDate= this.search.fromDate ? new Date( this.search.fromDate) : "";
      this.search.toDate= this.search.toDate ? new Date( this.search.toDate) : "";
    }, error => {
      this.search.fromDate= this.search.fromDate ? new Date( this.search.fromDate) : "";
      this.search.toDate= this.search.toDate ? new Date( this.search.toDate) : "";
      this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
    })
  }

  onSubmit() {
    this.internationalShipmentByRoad=[]
    this.search.fromDate=this.datePipe.transform(this.search.fromDate, 'yyyy-MM-dd')!=null?(this.datePipe.transform(this.search.fromDate, 'yyyy-MM-dd'))!:"";
    this.search.toDate=this.datePipe.transform(this.search.toDate, 'yyyy-MM-dd')!=null?(this.datePipe.transform(this.search.toDate, 'yyyy-MM-dd'))!:"";

    if (this.selectedBound.bound === "In bound") {
      this.getInboundSummary(this.search, 0, 10);
    } else if(this.selectedBound.bound === "Out bound"){
      this.getOutboundSummary(this.search, 0, 10);
    }else{
      this.getAllInternationalShipmentByRoad(this.search, 0, 10);
    }
  }
  onPageChange(event: any) {
    this.page = event.page;
    this.size = event.rows;
    if (this.selectedBound.bound === "In bound") {
    this.getInboundSummary(this.search, this.page, this.size);
    }
    else if(this.selectedBound.bound === "Out bound"){
    this.getOutboundSummary(this.search, this.page, this.size);
    }
    else{
      this.getAllInternationalShipmentByRoad(this.search, this.page, this.size);
    }
  }
}

interface Bound {
  bound: string
}
