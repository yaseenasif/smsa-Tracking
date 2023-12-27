import { Component } from '@angular/core';
import { MenuItem, MessageService } from 'primeng/api';
import { InternationalAirReportPerformance } from 'src/app/model/InternationalAirReportPerformance';
import { ReportService } from '../report.service';

@Component({
  selector: 'app-international-air-report-performance',
  templateUrl: './international-air-report-performance.component.html',
  styleUrls: ['./international-air-report-performance.component.scss'],
  providers:[MessageService]
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
  
  internationalAirReportPerformance!:InternationalAirReportPerformance[];
  constructor(private messageService:MessageService,private reportService:ReportService){}

 
   
  
  ngOnInit() {
      this.items = [{ label: 'Reports',routerLink:'/report-tiles'},{ label: 'International Shipment By Air Report Of Performance'}];
      this.getInternationalAirReportPerformance(this.searchBy);
    }
    
    getInternationalAirReportPerformance(searchBy:SearchBy){
      this.reportService.getInternationalAirReportPerformance(searchBy).subscribe((res:InternationalAirReportPerformance[])=>{
        this.internationalAirReportPerformance=res;
          },(error)=>{
            this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
          })
    }
    
    
}

interface SearchBy{
  fromDate:string,
  toDate:string,
  status:string,
  origin:string,
  destination:string,
  type:string,
  routeNumber:string
}
