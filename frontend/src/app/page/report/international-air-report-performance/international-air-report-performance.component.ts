import { Component } from '@angular/core';
import { MenuItem, MessageService } from 'primeng/api';
import { InternationalAirReportPerformance } from 'src/app/model/InternationalAirReportPerformance';
import { ReportService } from '../report.service';
import { ProductField } from 'src/app/model/ProductField';
import { ProductFieldServiceService } from '../../product-field/service/product-field-service.service';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-international-air-report-performance',
  templateUrl: './international-air-report-performance.component.html',
  styleUrls: ['./international-air-report-performance.component.scss'],
  providers:[MessageService,DatePipe]
})
export class InternationalAirReportPerformanceComponent {
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
  
  internationalAirReportPerformance!:InternationalAirReportPerformance[];
  constructor(private messageService:MessageService,private reportService:ReportService,private productFieldServiceService:ProductFieldServiceService,private datePipe:DatePipe){}

 
   
  
  ngOnInit() {
      this.items = [{ label: 'Reports',routerLink:'/report-tiles'},{ label: 'International Shipment By Air Report Of Performance'}];
      this.getAllShipmentStatus();
      this.getInternationalAirReportPerformance(this.searchBy);
    }
    
    getInternationalAirReportPerformance(searchBy:SearchBy){
      this.reportService.getInternationalAirReportPerformance(searchBy).subscribe((res:InternationalAirReportPerformance[])=>{
        this.internationalAirReportPerformance=res;
          },(error)=>{
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
    searchByFilter(){
      this.internationalAirReportPerformance=[]
      this.searchBy.fromDate=this.datePipe.transform(this.searchBy.fromDate, 'yyyy-MM-dd')!=null?(this.datePipe.transform(this.searchBy.fromDate, 'yyyy-MM-dd'))!:"";
      this.searchBy.toDate=this.datePipe.transform(this.searchBy.toDate, 'yyyy-MM-dd')!=null?(this.datePipe.transform(this.searchBy.fromDate, 'yyyy-MM-dd'))!:"";
      this.reportService.getInternationalAirReportPerformance(this.searchBy).subscribe((res:InternationalAirReportPerformance[])=>{
        this.internationalAirReportPerformance=res;
        this.searchBy.fromDate= this.searchBy.fromDate ? new Date( this.searchBy.fromDate) : "";
        this.searchBy.toDate= this.searchBy.toDate ? new Date( this.searchBy.toDate) : "";
          },(error)=>{
            this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
            this.searchBy.fromDate= this.searchBy.fromDate ? new Date( this.searchBy.fromDate) : "";
            this.searchBy.toDate= this.searchBy.toDate ? new Date( this.searchBy.toDate) : "";
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
    
    
}

interface SearchBy{
  fromDate:string|Date,
  toDate:string|Date,
  status:string,
  origin:string,
  destination:string,
  type:string,
  routeNumber:string
}
