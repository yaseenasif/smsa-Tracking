import { Component } from '@angular/core';
import { MenuItem, MessageService } from 'primeng/api';
import { ReportService } from '../report.service';
import { InternationalAirReportStatus } from 'src/app/model/InternationalAirReportStatus';
import { ProductFieldServiceService } from '../../product-field/service/product-field-service.service';
import { ProductField } from 'src/app/model/ProductField';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-international-air-report-status',
  templateUrl: './international-air-report-status.component.html',
  styleUrls: ['./international-air-report-status.component.scss'],
  providers: [MessageService,DatePipe]
})
export class InternationalAirReportStatusComponent {
  value!: any
  internationalAirReportStatus!: InternationalAirReportStatus[]

  items: MenuItem[] | undefined;
  searchBy: any = {
    fromDate: '',
    toDate: '',
    status: '',
    origin: '',
    destination: [],
    type: '',
    routeNumber: ''
  }
  shipmentStatus!:ProductField|null;

  constructor(private messageService: MessageService, private reportService: ReportService,private productFieldServiceService:ProductFieldServiceService,private datePipe:DatePipe) { }



  ngOnInit() {
    this.items = [{ label: 'Reports', routerLink: '/report-tiles' }, { label: 'International Shipment By Air Report Of Status' }];
    this.getInternationalAirReportStatus(this.searchBy);
  }

  searchByFilter(){
    this.internationalAirReportStatus=[]
    this.searchBy.fromDate=this.datePipe.transform(this.searchBy.fromDate, 'yyyy-MM-dd')!=null?(this.datePipe.transform(this.searchBy.fromDate, 'yyyy-MM-dd'))!:"";
    this.searchBy.toDate=this.datePipe.transform(this.searchBy.toDate, 'yyyy-MM-dd')!=null?(this.datePipe.transform(this.searchBy.fromDate, 'yyyy-MM-dd'))!:"";
    this.reportService.getInternationalAirReportStatus(this.searchBy).subscribe((res:InternationalAirReportStatus[]) => {
      this.internationalAirReportStatus = res;
      this.searchBy.fromDate= this.searchBy.fromDate ? new Date( this.searchBy.fromDate) : "";
      this.searchBy.toDate= this.searchBy.toDate ? new Date( this.searchBy.toDate) : "";
    }, (error) => { 
      this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
      this.searchBy.fromDate= this.searchBy.fromDate ? new Date( this.searchBy.fromDate) : "";
      this.searchBy.toDate= this.searchBy.toDate ? new Date( this.searchBy.toDate) : "";
    })
  }

  getInternationalAirReportStatus(searchBy: SearchBy) {
    this.reportService.getInternationalAirReportStatus(searchBy).subscribe((res:InternationalAirReportStatus[]) => {
      this.internationalAirReportStatus = res;
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
      destination: [],
      routeNumber: ''
    }
  }

  downloadFile() {
    this.searchBy.fromDate=this.datePipe.transform(this.searchBy.fromDate, 'yyyy-MM-dd')!=null?(this.datePipe.transform(this.searchBy.fromDate, 'yyyy-MM-dd'))!:"";
    this.searchBy.toDate=this.datePipe.transform(this.searchBy.toDate, 'yyyy-MM-dd')!=null?(this.datePipe.transform(this.searchBy.toDate, 'yyyy-MM-dd'))!:"";
 
    this.reportService.downloadReportExcel("/int-air-rep-status",this.searchBy,"International Air Report Status.xlsx");
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