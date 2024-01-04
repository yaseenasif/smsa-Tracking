import { Component } from '@angular/core';
import { MenuItem, MessageService } from 'primeng/api';
import { ReportService } from '../report.service';
import { InternationalRoadReportPerformance } from 'src/app/model/InternationalRoadReportPerformance';
import { ProductField } from 'src/app/model/ProductField';
import { ProductFieldServiceService } from '../../product-field/service/product-field-service.service';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-international-road-report-performance',
  templateUrl: './international-road-report-performance.component.html',
  styleUrls: ['./international-road-report-performance.component.scss'],
  providers:[MessageService,DatePipe]
})
export class InternationalRoadReportPerformanceComponent {
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
  shipmentStatus!:ProductField|null;
  
  constructor(private messageService: MessageService, private reportService: ReportService,private productFieldServiceService:ProductFieldServiceService,private datePipe:DatePipe) { }
  internationalRoadReportPerformance!:InternationalRoadReportPerformance[];
   
  
  ngOnInit() {
      this.items = [{ label: 'Reports',routerLink:'/report-tiles'},{ label: 'International Shipment By Road Report Of Performance'}];
      this.getInternationalRoadReportPerformance(this.searchBy);
  }
  searchByFilter(){
    this.internationalRoadReportPerformance=[]
    this.searchBy.fromDate=this.datePipe.transform(this.searchBy.fromDate, 'yyyy-MM-dd')!=null?(this.datePipe.transform(this.searchBy.fromDate, 'yyyy-MM-dd'))!:"";
    this.searchBy.toDate=this.datePipe.transform(this.searchBy.toDate, 'yyyy-MM-dd')!=null?(this.datePipe.transform(this.searchBy.toDate, 'yyyy-MM-dd'))!:"";
    this.reportService.getInternationalRoadReportPerformance(this.searchBy).subscribe((res:InternationalRoadReportPerformance[]) => {
      this.internationalRoadReportPerformance = res;
      this.searchBy.fromDate= this.searchBy.fromDate ? new Date( this.searchBy.fromDate) : "";
      this.searchBy.toDate= this.searchBy.toDate ? new Date( this.searchBy.toDate) : "";
    }, (error) => { 
      this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
      this.searchBy.fromDate= this.searchBy.fromDate ? new Date( this.searchBy.fromDate) : "";
      this.searchBy.toDate= this.searchBy.toDate ? new Date( this.searchBy.toDate) : "";
    })
  }

  getInternationalRoadReportPerformance(searchBy:SearchBy){
    this.reportService.getInternationalRoadReportPerformance(searchBy).subscribe((res:InternationalRoadReportPerformance[]) => {
      this.internationalRoadReportPerformance = res;
    }, (error) => { 
      this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
    })
  }
  getAllShipmentStatus() {
    this.productFieldServiceService.getProductFieldByName("Search_For_International_By_Road").subscribe((res: ProductField) => {
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
      destination: '',
      type: '',
      routeNumber: ''
    }
  }
  downloadFile() {
    this.searchBy.fromDate=this.datePipe.transform(this.searchBy.fromDate, 'yyyy-MM-dd')!=null?(this.datePipe.transform(this.searchBy.fromDate, 'yyyy-MM-dd'))!:"";
    this.searchBy.toDate=this.datePipe.transform(this.searchBy.toDate, 'yyyy-MM-dd')!=null?(this.datePipe.transform(this.searchBy.fromDate, 'yyyy-MM-dd'))!:"";

    this.reportService.downloadReportExcel("/int-road-rep-per",this.searchBy,"International Road Report Performance.xlsx");
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