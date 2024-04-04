import { PaginatedResponse } from '../../../../../model/PaginatedResponse';
import { Component } from '@angular/core';
import { MenuItem, MessageService } from 'primeng/api';
import { ShipmentStatus } from 'src/app/model/ShipmentStatus';
import { SummaryService } from '../../../service/summary.service';
import { ShipmentStatusService } from 'src/app/page/shipment-status/service/shipment-status.service';
import { DatePipe } from '@angular/common';
import { InternationalSummarySearch } from 'src/app/model/InternationalSummarySearch'
import { AuthguardService } from 'src/app/auth-service/authguard/authguard.service';
import { ProductField } from 'src/app/model/ProductField';
import { ProductFieldServiceService } from 'src/app/page/product-field/service/product-field-service.service';
import { InternationalShippingService } from 'src/app/page/shipping-order/international/service/international-shipping.service';
@Component({
  selector: 'app-international-summary-by-air',
  templateUrl: './international-summary-by-air.component.html',
  styleUrls: ['./international-summary-by-air.component.scss'],
  providers: [DatePipe, MessageService]
})
export class InternationalSummaryByAirComponent {

  name!: string;
  bound!: Bound[];
  // shipmentStatus!: ShipmentStatus[];
  shipmentStatus!: ProductField | null | undefined;
  role: any;

  selectedBound: Bound = {
    bound: "All"
  };

  paginationRes: any;

  search: any = {
    fromDate: "",
    toDate: "",
    status: "",
    origin: "",
    destinations: [],
    type: "",
    routeNumber:""
  }
  page: number = 0;
  size: number = 10;

  constructor(
    private summaryService: SummaryService,
    private messageService: MessageService,
    private datePipe: DatePipe,
    private authguardService: AuthguardService,
    private shipmentStatusService: ProductFieldServiceService,
    private internationalShippingService:InternationalShippingService,
  ) { }

  internationalShipmentByAir: any = [];
  items: MenuItem[] | undefined;

  hasPermission(permission:string):boolean{
    return this.authguardService.hasPermission(permission)
  }

  ngOnInit() {
    this.getRole()

    this.items = [{ label: 'International Inbound By Air' }];
    // this.bound = [
    //   {
    //     bound: "In bound"
    //   },
    //   {
    //     bound: "Out bound"
    //   },
    //   {
    //     bound: "All"
    //   }
    // ]
    this.getAllShipmentStatus();
    this.getInboundSummary(this.search, 0, 10);
    // this.getAllInternationalShipmentByAir(this.search, 0, 10);
  }

  getRole() {
    const token = localStorage.getItem('accessToken');

    const decodeToken = this.authguardService.getDecodedAccessToken(token!);

    this.role = decodeToken.ROLES;
    console.log(this.role);

  }

  onBoundChange() {
  //  this.clear();
  //  this.internationalShipmentByAir=[];
    // if (this.selectedBound && this.selectedBound.bound === "In bound") {
    //   this.getInboundSummary(this.search, 0, 10);
    // } else if (this.selectedBound && this.selectedBound.bound === "Out bound") {
    //   this.getOutboundSummary(this.search, 0, 10);
    // }
    // else{
    //   this.getAllInternationalShipmentByAir(this.search, 0, 10);
    // }

  }
  clear(){
    this.search = {
      fromDate: "",
      toDate: "",
      status: "",
      origin: "",
      destinations: [],
      type: "",
      routeNumber:""
    }
  }
  getAllShipmentStatus() {
    this.shipmentStatusService.getProductFieldByName("Search_For_International_By_Air").subscribe((res: ProductField) => {
      this.shipmentStatus = res;
    }, error => {

      this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });

    })
  }




  getInboundSummary(obj: InternationalSummarySearch, page: number, size: number) {
    this.internationalShipmentByAir=[]
    this.summaryService.getInboundSummaryForAir(obj, page, size).subscribe((res: any) => {
      this.internationalShipmentByAir = res.content;
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
    this.summaryService.getOutboundSummaryForAir(obj, page, size).subscribe((res: any) => {
      this.internationalShipmentByAir = res.content;
      this.paginationRes = res;
      this.search.fromDate= this.search.fromDate ? new Date( this.search.fromDate) : "";
      this.search.toDate= this.search.toDate ? new Date( this.search.toDate) : "";
    }, (error: any) => {
      this.search.fromDate= this.search.fromDate ? new Date( this.search.fromDate) : "";
      this.search.toDate= this.search.toDate ? new Date( this.search.toDate) : "";
      this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
    })
  }
  getAllInternationalShipmentByAir(obj: InternationalSummarySearch, page: number, size: number) {
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
    this.internationalShippingService.getAllInternationalShipmentByAir(objForAll, this.page , this.size).subscribe((res: any) => {
      this.internationalShipmentByAir = res.content;
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
    this.internationalShipmentByAir=[]
    this.search.fromDate=this.datePipe.transform(this.search.fromDate, 'yyyy-MM-dd')!=null?(this.datePipe.transform(this.search.fromDate, 'yyyy-MM-dd'))!:"";
    this.search.toDate=this.datePipe.transform(this.search.toDate, 'yyyy-MM-dd')!=null?(this.datePipe.transform(this.search.toDate, 'yyyy-MM-dd'))!:"";
    this.getInboundSummary(this.search, 0, 10);
    // if (this.selectedBound.bound === "In bound") {
    //   this.getInboundSummary(this.search, 0, 10);
    // }
    // else if(this.selectedBound.bound === "Out bound") {
    //   this.getOutboundSummary(this.search, 0, 10);
    // }else{
    //   this.getAllInternationalShipmentByAir(this.search, 0, 10);
    // }
  }
  onPageChange(event: any) {

    this.page = event.page;
    this.size = event.rows;
    this.getInboundSummary(this.search, this.page, this.size);
    // if (this.selectedBound.bound === "In bound") {
    // this.getInboundSummary(this.search, this.page, this.size);
    // }
    // else if(this.selectedBound.bound === "Out bound") {
    // this.getOutboundSummary(this.search, this.page, this.size);
    // }
    // else{
    //   this.getAllInternationalShipmentByAir(this.search,this.page,this.size);
    // }
  }
}

interface Bound {
  bound: string
}
