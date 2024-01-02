import { Component } from '@angular/core';
import { MenuItem, MessageService } from 'primeng/api';
import { ReportService } from '../report.service';
import { InternationalRoadReportStatus } from 'src/app/model/InternationalRoadReportStatus';
import { ProductField } from 'src/app/model/ProductField';
import { ProductFieldServiceService } from '../../product-field/service/product-field-service.service';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-international-road-report-status',
  templateUrl: './international-road-report-status.component.html',
  styleUrls: ['./international-road-report-status.component.scss'],
  providers:[MessageService,DatePipe]
})
export class InternationalRoadReportStatusComponent {
  value!:any

  items: MenuItem[] | undefined;
  searchBy:SearchBy={
    fromDate: '',
    toDate: '',
    status: '',
    origin: '',
    destination: '',
    type: '',
    routeNumber: ''
  }
  internationalRoadReportStatus!:InternationalRoadReportStatus[];
  shipmentStatus!:ProductField|null;
  constructor(private messageService: MessageService, private reportService: ReportService,private productFieldServiceService:ProductFieldServiceService,private datePipe:DatePipe) { }
  
   
  
  ngOnInit() {
      this.items = [{ label: 'Reports',routerLink:'/report-tiles'},{ label: 'International Shipment By Road Report Of Status'}];
      this.getInternationalRoadReportStatus(this.searchBy)
  }

  searchByFilter(){
    this.internationalRoadReportStatus=[]
    this.searchBy.fromDate=this.datePipe.transform(this.searchBy.fromDate, 'yyyy-MM-dd')!=null?(this.datePipe.transform(this.searchBy.fromDate, 'yyyy-MM-dd'))!:"";
    this.searchBy.toDate=this.datePipe.transform(this.searchBy.toDate, 'yyyy-MM-dd')!=null?(this.datePipe.transform(this.searchBy.fromDate, 'yyyy-MM-dd'))!:"";
    this.reportService.getInternationalRoadReportStatus(this.searchBy).subscribe((res:InternationalRoadReportStatus[]) => {
      this.internationalRoadReportStatus = res;
      this.searchBy.fromDate= this.searchBy.fromDate ? new Date( this.searchBy.fromDate) : "";
      this.searchBy.toDate= this.searchBy.toDate ? new Date( this.searchBy.toDate) : "";
    }, (error) => { 
      this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
      this.searchBy.fromDate= this.searchBy.fromDate ? new Date( this.searchBy.fromDate) : "";
      this.searchBy.toDate= this.searchBy.toDate ? new Date( this.searchBy.toDate) : "";
    })
  }

  getInternationalRoadReportStatus(searchBy:SearchBy){
    this.reportService.getInternationalRoadReportStatus(searchBy).subscribe((res:InternationalRoadReportStatus[]) => {
      this.internationalRoadReportStatus = res;
    }, (error) => { 
      this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
    })
  }
  getAllShipmentStatus() {
    this.productFieldServiceService.getProductFieldByName("Search_For_International_By_Air").subscribe((res: ProductField) => {
      this.shipmentStatus = res;
    }, (error) => {
      this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
    })
  }
  clearFilter(){
    this.searchBy={
      fromDate: '',
      toDate: '',
      status: '',
      origin: '',
      type: '',
      destination: '',
      routeNumber: ''
    }
  }
  downloadFile() {
    this.searchBy.fromDate=this.datePipe.transform(this.searchBy.fromDate, 'yyyy-MM-dd')!=null?(this.datePipe.transform(this.searchBy.fromDate, 'yyyy-MM-dd'))!:"";
    this.searchBy.toDate=this.datePipe.transform(this.searchBy.toDate, 'yyyy-MM-dd')!=null?(this.datePipe.transform(this.searchBy.fromDate, 'yyyy-MM-dd'))!:"";

    this.reportService.downloadReportExcel("/int-road-rep-status",this.searchBy);
  }
}

interface SearchBy {
  fromDate:string|Date;
  toDate:string|Date;
  status: string;
  origin: string;
  destination: string;
  type: string;
  routeNumber: string;
}
