import { Component } from '@angular/core';
import { MenuItem, MessageService } from 'primeng/api';
import { DomesticPerformance } from 'src/app/model/DomesticPerformance';
import { ReportService } from '../report.service';
import { ProductFieldServiceService } from '../../product-field/service/product-field-service.service';
import { ProductField } from 'src/app/model/ProductField';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-domestic-report-performance',
  templateUrl: './domestic-report-performance.component.html',
  styleUrls: ['./domestic-report-performance.component.scss'],
  providers:[MessageService,DatePipe]
})
export class DomesticReportPerformanceComponent {

value!:any

items: MenuItem[] | undefined;

constructor(private messageService:MessageService,private reportService:ReportService,private datePipe:DatePipe,private productFieldServiceService: ProductFieldServiceService){}
domesticPerformance!:DomesticPerformance[]
searchBy:SearchBy={
  fromDate: '',
  toDate: '',
  status: '',
  origin: '',
  destination: '',
  routeNumber: ''
}
shipmentStatus!:ProductField|null;

ngOnInit() {
    this.items = [{ label: 'Reports',routerLink:'/report-tiles'},{ label: 'Domestic Shipment Of Performance'}];
    this.getAllShipmentStatus();
    this.getDomesticReportPerformance(this.searchBy);
}


getDomesticReportPerformance(searchBy:SearchBy){
this.reportService.getDomesticReportPerformance(searchBy).subscribe((res:DomesticPerformance[])=>{
  this.domesticPerformance=res;
},(error)=>{
  this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
})
}

searchByFilter(){
  this.searchBy.fromDate=this.datePipe.transform(this.searchBy.fromDate, 'yyyy-MM-dd')!=null?(this.datePipe.transform(this.searchBy.fromDate, 'yyyy-MM-dd'))!:"";
  this.searchBy.toDate=this.datePipe.transform(this.searchBy.toDate, 'yyyy-MM-dd')!=null?(this.datePipe.transform(this.searchBy.fromDate, 'yyyy-MM-dd'))!:"";
  this.domesticPerformance=[]
  this.reportService.getDomesticReportPerformance(this.searchBy).subscribe((res:DomesticPerformance[])=>{
    this.domesticPerformance=res;
    this.searchBy.fromDate= this.searchBy.fromDate ? new Date( this.searchBy.fromDate) : "";
    this.searchBy.toDate= this.searchBy.toDate ? new Date( this.searchBy.toDate) : "";
  },(error)=>{
    this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
    this.searchBy.fromDate= this.searchBy.fromDate ? new Date( this.searchBy.fromDate) : "";
    this.searchBy.toDate= this.searchBy.toDate ? new Date( this.searchBy.toDate) : "";
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
    destination: '',
    routeNumber: ''
  }
}

}

interface SearchBy{
  fromDate:string|Date,
  toDate:string|Date,
  status:string,
  origin:string,
  destination:string,
  routeNumber:string
}

