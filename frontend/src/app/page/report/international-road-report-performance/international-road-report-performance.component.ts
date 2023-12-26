import { Component } from '@angular/core';
import { MenuItem, MessageService } from 'primeng/api';

@Component({
  selector: 'app-international-road-report-performance',
  templateUrl: './international-road-report-performance.component.html',
  styleUrls: ['./international-road-report-performance.component.scss'],
  providers:[MessageService]
})
export class InternationalRoadReportPerformanceComponent {
  value!:any

  items: MenuItem[] | undefined;
  
  constructor(private messageService:MessageService){}
  
   
  
  ngOnInit() {
      this.items = [{ label: 'Reports',routerLink:'/report-tiles'},{ label: 'International Shipment By Road Report Of Performance'}];
  }
}
