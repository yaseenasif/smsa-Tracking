import { PaginatedResponse } from '../../../../../model/PaginatedResponse';
import { Component } from '@angular/core';
import { MenuItem, MessageService } from 'primeng/api';
import { InternationalShipment } from 'src/app/model/InternationalShipment';
import { InternationalShippingService } from '../../service/international-shipping.service';
import { ShipmentStatus } from 'src/app/model/ShipmentStatus';
import { ShipmentStatusService } from 'src/app/page/shipment-status/service/shipment-status.service';
import { DatePipe } from '@angular/common';
import { ProductField } from 'src/app/model/ProductField';
import { ProductFieldServiceService } from 'src/app/page/product-field/service/product-field-service.service';

@Component({
  selector: 'app-international-shipment-list-air',
  templateUrl: './international-shipment-list-air.component.html',
  styleUrls: ['./international-shipment-list-air.component.scss'],
  providers: [DatePipe,MessageService]
})
export class InternationalShipmentListAirComponent {

  internationalShipmentByAir!: InternationalShipment[];
  paginationRes: any;
  page: number = 0;
  size: number = 10;
  visible:boolean=false;
  ISid!:number;

  // shipmentStatus!: ShipmentStatus[];
  shipmentStatus!: ProductField | null | undefined;

  fromDate: string = '';
  toDate: string = '';
  status: string = '';
  origin: string = '';
  destination: string = '';
  routeNumber: string = '';

  constructor(
    private internationalShippingService: InternationalShippingService,
    private messageService:MessageService,
    private datePipe:DatePipe,
    // private shipmentStatusService: ShipmentStatusService,
    private shipmentStatusService: ProductFieldServiceService,
    ) { }
  items: MenuItem[] | undefined;
  searchedValue: string = '';


  ngOnInit() {
    this.items = [{ label: 'International Shipment', routerLink: '/international-tile' }, { label: 'International Shipment By Air' }];
    this.getAllInternationalShipmentByAir(this.fromDate,this.toDate,this.status,this.origin,this.destination,this.routeNumber, undefined, undefined);
    this.getAllShipmentStatus();
  }


  getAllInternationalShipmentByAir(fromDate?: string,toDate?: string,status?: string,origin?: string,destination?: string,routeNumber?: string, page?: number, size?: number) {
    this.internationalShippingService.getAllInternationalShipmentByAir({ fromDate:this.fromDate?this.datePipe.transform(new Date(this.fromDate),'yyyy-MM-dd'):'',toDate:this.toDate?this.datePipe.transform(new Date(this.toDate),'yyyy-MM-dd'):'',status: status,origin: origin,destination: destination,routeNumber: routeNumber, user: {} ,type:"",activeStatus:true}, this.page , this.size).subscribe((res: any) => {
      this.internationalShipmentByAir = res.content;
      this.paginationRes = res;
    }, error => {
      this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
    })
  }

  getAllShipmentStatus() {
    this.shipmentStatusService.getProductFieldByName("Search_For_International_By_Air").subscribe((res: ProductField) => {
      this.shipmentStatus = res;
    }, error => {
      this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
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


    deleteInternationalShipmentByID(id:number){
      this.internationalShippingService.deleteInternationalShipmentByID(id).subscribe((res)=>{
        this.messageService.add({ severity: 'success', summary: 'Success', detail:"shipment is deleted successfully"});
        this.getAllInternationalShipmentByAir(this.fromDate ,this.toDate,this.status,this.origin,this.destination,this.routeNumber, this.page, this.size);
        this.visible=false;
      },(error)=>{
        this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
        this.visible=false;
      })
    }

    showModal(id:number){
     this.visible=true
     this.ISid=id
    }
}
