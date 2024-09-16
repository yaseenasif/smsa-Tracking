import { Component } from '@angular/core';
import { MenuItem } from 'primeng/api';
import { InternationalShippingService } from '../../service/international-shipping.service';
import { InternationalShipment } from '../../../../../model/InternationalShipment';
import { ShipmentStatus } from 'src/app/model/ShipmentStatus';
import { ShipmentStatusService } from 'src/app/page/shipment-status/service/shipment-status.service';
import { DatePipe } from '@angular/common';
import { MessageService } from 'primeng/api';
import { ProductField } from 'src/app/model/ProductField';
import { ProductFieldServiceService } from 'src/app/page/product-field/service/product-field-service.service';
import { AuthguardService } from 'src/app/auth-service/authguard/authguard.service';

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

  // shipmentStatus!: ShipmentStatus[];
  shipmentStatus!: ProductField | null | undefined;

  fromDate: string = '';
  toDate: string = '';
  status: string = '';
  origin: string []= [];
  destination: string = '';
  routeNumber: string = '';
  ISid!: number;
  visible: boolean=false;

  constructor(
    private internationalShippingService: InternationalShippingService,
    private datePipe:DatePipe,
    private messageService:MessageService,
    // private shipmentStatusService: ShipmentStatusService
    private shipmentStatusService: ProductFieldServiceService,
    private authguardService:AuthguardService
    ) { }
  items: MenuItem[] | undefined;


  ngOnInit() {
    this.items = [{ label: 'International Outbound', routerLink: '/international-tile' }, { label: 'International Outbound By Road' }];
    this.getAllInternationalShipmentByRoad(this.fromDate,this.toDate,this.status,this.origin,this.destination,this.routeNumber, undefined, undefined);
    this.getAllShipmentStatus();
  }

  hasPermission(permission:string):boolean{
    return this.authguardService.hasPermission(permission)
  }

  getAllInternationalShipmentByRoad(fromDate?: string,toDate?: string,status?: string,origin?: string[],destination?: string,routeNumber?: string, page?: number, size?: number) {
    this.internationalShippingService.getAllInternationalShipmentByRoadForOutBound({ fromDate:this.fromDate?this.datePipe.transform(new Date(this.fromDate),'yyyy-MM-dd'):'',toDate:this.toDate?this.datePipe.transform(new Date(this.toDate),'yyyy-MM-dd'):'',status: status,origin: origin,destinations: destination,routeNumber: routeNumber}, this.page , this.size).subscribe((res: any) => {

      this.internationalShipmentByRoad = res.content;
      this.paginatedRes = res;
    }, error => {
      this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
    })
  }

  getAllShipmentStatus() {
    this.shipmentStatusService.getProductFieldByName("Search_For_International_By_Road").subscribe((res: ProductField) => {
      this.shipmentStatus = res;
    }, error => {
      this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
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
    this.origin = [];
    this.destination = '';
    this.routeNumber = '';
    this.getAllInternationalShipmentByRoad(this.fromDate,this.toDate,this.status,this.origin,this.destination,this.routeNumber, undefined, undefined);
    }


    deleteInternationalShipmentByID(id:number){
      this.internationalShippingService.deleteInternationalShipmentByID(id).subscribe((res)=>{
        this.messageService.add({ severity: 'success', summary: 'Success', detail:"shipment is deleted successfully"});
        this.getAllInternationalShipmentByRoad(this.fromDate ,this.toDate,this.status,this.origin,this.destination,this.routeNumber, this.page, this.size);
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

    getColor(internationalShipmentByRoad:any) {
      if (internationalShipmentByRoad.refrigeratedTruck) {
        return 'blue';
      } else {
        return 'withoutRed';
      }
    }
}
