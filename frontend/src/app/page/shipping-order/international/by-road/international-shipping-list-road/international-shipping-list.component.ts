import { Component } from '@angular/core';
import { MenuItem } from 'primeng/api';
import { InternationalShippingService } from '../../service/international-shipping.service';
import { InternationalShipment } from '../../../../../model/InternationalShipment';

@Component({
  selector: 'app-international-shipping-list',
  templateUrl: './international-shipping-list.component.html',
  styleUrls: ['./international-shipping-list.component.scss']
})
export class InternationalShippingListComponent {
  internationalShipmentByRoad!: InternationalShipment[];
  paginatedRes: any;
  page: number = 0;
  size: number = 10;
  searchedValue: string = '';
  constructor(private internationalShippingService: InternationalShippingService) { }
  items: MenuItem[] | undefined;


  ngOnInit() {
    this.items = [{ label: 'International Shipment', routerLink: '/international-tile' }, { label: 'International Shipment By Road' }];
    this.getAllInternationalShipmentByRoad(this.searchedValue, this.page, this.size);
  }


  getAllInternationalShipmentByRoad(searchedValue?: string, page?: number, size?: number) {
    this.internationalShippingService.getAllInternationalShipmentByRoad({ value: searchedValue, user: {}, type: '' }, page, size).subscribe((res: any) => {
      debugger
      this.internationalShipmentByRoad = res.content;
      this.paginatedRes = res;
    }, error => {
    })
  }

  onPageChange(event: any) {
    this.page = event.page;
    this.size = event.rows;
    this.getAllInternationalShipmentByRoad(this.searchedValue, this.page, this.size);
  }
}
