import { PaginatedResponse } from '../../../../../model/PaginatedResponse';
import { Component } from '@angular/core';
import { MenuItem } from 'primeng/api';
import { InternationalShipment } from 'src/app/model/InternationalShipment';
import { InternationalShippingService } from '../../service/international-shipping.service';
import { ShipmentStatus } from 'src/app/model/ShipmentStatus';
import { ShipmentStatusService } from 'src/app/page/shipment-status/service/shipment-status.service';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-international-shipment-list-air',
  templateUrl: './international-shipment-list-air.component.html',
  styleUrls: ['./international-shipment-list-air.component.scss'],
  providers: [DatePipe]
})
export class InternationalShipmentListAirComponent {

  internationalShipmentByAir!: InternationalShipment[];
  paginationRes: any;
  page: number = 0;
  size: number = 10;

  shipmentStatus!: ShipmentStatus[];

  fromDate: string = '';
  toDate: string = '';
  status: string = '';
  origin: string = '';
  destination: string = '';
  routeNumber: string = '';

  constructor(private internationalShippingService: InternationalShippingService,
    private datePipe:DatePipe,
    private shipmentStatusService: ShipmentStatusService,) { }
  items: MenuItem[] | undefined;
  searchedValue: string = '';


  ngOnInit() {
    this.items = [{ label: 'International Shipment', routerLink: '/international-tile' }, { label: 'International Shipment By Air' }];
    this.getAllInternationalShipmentByAir(this.fromDate,this.toDate,this.status,this.origin,this.destination,this.routeNumber, undefined, undefined);
  }


  getAllInternationalShipmentByAir(fromDate?: string,toDate?: string,status?: string,origin?: string,destination?: string,routeNumber?: string, page?: number, size?: number) {
    debugger
    this.internationalShippingService.getAllInternationalShipmentByAir({ fromDate:this.fromDate?this.datePipe.transform(new Date(this.fromDate),'yyyy-MM-dd'):'',toDate:this.toDate?this.datePipe.transform(new Date(this.toDate),'yyyy-MM-dd'):'',status: status,origin: origin,destination: destination,routeNumber: routeNumber, user: {} ,type:"",activeStatus:true}, this.page , this.size).subscribe((res: any) => {
      this.internationalShipmentByAir = res.content;
      debugger
      this.paginationRes = res;
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
    
    this.getAllInternationalShipmentByAir(this.fromDate ,this.toDate,this.status,this.origin,this.destination,this.routeNumber, this.page, this.size);
  }

  searchByFilter(){
    this.getAllInternationalShipmentByAir(this.fromDate,this.toDate,this.status,this.origin,this.destination,this.routeNumber, undefined, undefined);
  }

  clearFilter(){
    this.fromDate = '';
    this.toDate = '';
    this.status = '';
    this.origin = '';
    this.destination = '';
    this.routeNumber = '';
    this.getAllInternationalShipmentByAir(this.fromDate,this.toDate,this.status,this.origin,this.destination,this.routeNumber, undefined, undefined);
    }
}
