import { Component } from '@angular/core';
import { MenuItem, MessageService } from 'primeng/api';

@Component({
  selector: 'app-international-road-report-status',
  templateUrl: './international-road-report-status.component.html',
  styleUrls: ['./international-road-report-status.component.scss'],
  providers:[MessageService]
})
export class InternationalRoadReportStatusComponent {
  value!:any

  items: MenuItem[] | undefined;
  
  constructor(private messageService:MessageService){}
  
   
  
  ngOnInit() {
      this.items = [{ label: 'Reports',routerLink:'/report-tiles'},{ label: 'International Shipment By Road Report Of Status'}];
  }
}
