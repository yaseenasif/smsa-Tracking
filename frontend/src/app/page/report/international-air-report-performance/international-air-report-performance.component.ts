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
  
  internationalAirReportPerformance!:InternationalAirReportPerformance[];
  constructor(private messageService:MessageService,private reportService:ReportService){}

 
   
  
  ngOnInit() {
      this.items = [{ label: 'Reports',routerLink:'/report-tiles'},{ label: 'International Shipment By Air Report Of Performance'}];
      this.getInternationalAirReportPerformance();
    }
    
    getInternationalAirReportPerformance(){
      this.reportService.getInternationalAirReportPerformance().subscribe((res:InternationalAirReportPerformance[])=>{
        this.internationalAirReportPerformance=res;
          },(error)=>{
            this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
          })
    }
    
    
}
