import { PaginatedResponse } from '../../../../../model/PaginatedResponse';
import { Component } from '@angular/core';
import { MenuItem } from 'primeng/api';
import { InternationalShipment } from 'src/app/model/InternationalShipment';
import { InternationalShippingService } from '../../service/international-shipping.service';

@Component({
  selector: 'app-international-shipment-list-air',
  templateUrl: './international-shipment-list-air.component.html',
  styleUrls: ['./international-shipment-list-air.component.scss']
})
export class InternationalShipmentListAirComponent {

  internationalShipmentByAir!: InternationalShipment[];
  paginationRes: any;
  page: number = 0;
  size: number = 10;

  constructor(private internationalShippingService: InternationalShippingService) { }
  items: MenuItem[] | undefined;
  searchedValue: string = '';


  ngOnInit() {
    this.items = [{ label: 'International Shipment', routerLink: '/international-tile' }, { label: 'International Shipment By Air' }];
    this.getAllInternationalShipmentByAir(this.searchedValue , this.page , this.size);
  }


  getAllInternationalShipmentByAir(searchedValue?: string, page?: number, size?: number) {
    debugger
    this.internationalShippingService.getAllInternationalShipmentByAir({ value: searchedValue, user: {}, type: "" }, this.page = page!, this.size = size!).subscribe((res: any) => {
      this.internationalShipmentByAir = res.content;
      debugger
      this.paginationRes = res;
    }, error => {

    })
  }
  onPageChange(event: any) {
    this.page = event.first;
    this.size = event.rows;
    debugger
    this.getAllInternationalShipmentByAir(this.searchedValue, this.page, this.size);
  }
}
