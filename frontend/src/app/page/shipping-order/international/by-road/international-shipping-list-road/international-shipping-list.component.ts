import { Component } from '@angular/core';
import { MenuItem, MessageService } from 'primeng/api';
import { InternationalShippingService } from '../../service/international-shipping.service';
import { InternationalShipment } from '../../../../../model/InternationalShipment';
import { ShipmentStatus } from 'src/app/model/ShipmentStatus';
import { ShipmentStatusService } from 'src/app/page/shipment-status/service/shipment-status.service';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-international-shipping-list',
  templateUrl: './international-shipping-list.component.html',
  styleUrls: ['./international-shipping-list.component.scss'],
  providers: [DatePipe,MessageService]
})
export class InternationalShippingListComponent {
  internationalShipmentByRoad!: InternationalShipment[];
  paginatedRes: any;
  page: number = 0;
  size: number = 10;
  searchedValue: string = '';

  shipmentStatus!: ShipmentStatus[];

  fromDate: string = '';
  toDate: string = '';
  status: string = '';
  origin: string = '';
  destination: string = '';
  routeNumber: string = '';

  constructor(private internationalShippingService: InternationalShippingService,
    private datePipe:DatePipe,
    private shipmentStatusService: ShipmentStatusService,
    private messageService:MessageService) { }
  items: MenuItem[] | undefined;


  ngOnInit() {
    this.items = [{ label: 'International Shipment', routerLink: '/international-tile' }, { label: 'International Shipment By Road' }];
    this.getAllInternationalShipmentByRoad(this.fromDate,this.toDate,this.status,this.origin,this.destination,this.routeNumber, undefined, undefined);
    this.getAllShipmentStatus();
  }


  getAllInternationalShipmentByRoad(fromDate?: string,toDate?: string,status?: string,origin?: string,destination?: string,routeNumber?: string, page?: number, size?: number) {
    this.internationalShippingService.getAllInternationalShipmentByRoad({ fromDate:this.fromDate?this.datePipe.transform(new Date(this.fromDate),'yyyy-MM-dd'):'',toDate:this.toDate?this.datePipe.transform(new Date(this.toDate),'yyyy-MM-dd'):'',status: status,origin: origin,destination: destination,routeNumber: routeNumber, user: {} ,type:"",activeStatus:true}, this.page , this.size).subscribe((res: any) => {
      debugger
      this.internationalShipmentByRoad = res.content;
      this.paginatedRes = res;
    }, error => {
    })
  }

  getAllShipmentStatus() {
    this.shipmentStatusService.getALLShipmentStatus().subscribe((res: ShipmentStatus[]) => {
      this.shipmentStatus = res;
    }, error => {

   
    })
  }

  onPageChange(event: any) {
    this.page = event.page;
    this.size = event.rows;
    this.getAllInternationalShipmentByRoad(this.fromDate ,this.toDate,this.status,this.origin,this.destination,this.routeNumber, this.page, this.size);
  }

  searchByFilter(){
    this.getAllInternationalShipmentByRoad(this.fromDate,this.toDate,this.status,this.origin,this.destination,this.routeNumber, undefined, undefined);
  }
  clearFilter(){
    this.fromDate = '';
    this.toDate = '';
    this.status = '';
    this.origin = '';
    this.destination = '';
    this.routeNumber = '';
    this.getAllInternationalShipmentByRoad(this.fromDate,this.toDate,this.status,this.origin,this.destination,this.routeNumber, undefined, undefined);
    }
}
