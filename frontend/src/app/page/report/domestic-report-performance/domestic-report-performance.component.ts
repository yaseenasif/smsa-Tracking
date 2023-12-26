import { Component } from '@angular/core';
import { MenuItem, MessageService } from 'primeng/api';

@Component({
  selector: 'app-domestic-report-performance',
  templateUrl: './domestic-report-performance.component.html',
  styleUrls: ['./domestic-report-performance.component.scss'],
  providers:[MessageService]
})
export class DomesticReportPerformanceComponent {

value!:any

items: MenuItem[] | undefined;

constructor(private messageService:MessageService){}

 

ngOnInit() {
    this.items = [{ label: 'Reports',routerLink:'/report-tiles'},{ label: 'Domestic Shipment Of Performance'}];
}

}
