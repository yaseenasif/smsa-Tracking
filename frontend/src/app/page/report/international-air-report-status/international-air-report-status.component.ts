import { Component } from '@angular/core';
import { MenuItem, MessageService } from 'primeng/api';

@Component({
  selector: 'app-international-air-report-status',
  templateUrl: './international-air-report-status.component.html',
  styleUrls: ['./international-air-report-status.component.scss'],
  providers:[MessageService]
})
export class InternationalAirReportStatusComponent {
  value!:any

  items: MenuItem[] | undefined;
  
  constructor(private messageService:MessageService){}
  
   
  
  ngOnInit() {
      this.items = [{ label: 'Reports',routerLink:'/report-tiles'},{ label: 'International Shipment By Air Report Of Status'}];
  }
}
