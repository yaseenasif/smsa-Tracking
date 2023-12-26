import { Component } from '@angular/core';
import { MenuItem, MessageService } from 'primeng/api';

@Component({
  selector: 'app-international-air-report-performance',
  templateUrl: './international-air-report-performance.component.html',
  styleUrls: ['./international-air-report-performance.component.scss'],
  providers:[MessageService]
})
export class InternationalAirReportPerformanceComponent {
  value!:any

  items: MenuItem[] | undefined;
  
  constructor(private messageService:MessageService){}
  
   
  
  ngOnInit() {
      this.items = [{ label: 'Reports',routerLink:'/report-tiles'},{ label: 'International Shipment By Air Report Of Performance'}];
  }
}
