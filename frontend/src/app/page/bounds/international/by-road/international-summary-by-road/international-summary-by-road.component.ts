import { Component } from '@angular/core';
import { MenuItem, MessageService } from 'primeng/api';
import { SummaryService } from '../../../service/summary.service';
import { DatePipe } from '@angular/common';
import { ShipmentStatusService } from 'src/app/page/shipment-status/service/shipment-status.service';
import { ShipmentStatus } from 'src/app/model/ShipmentStatus';
import { InternationalSummarySearch } from 'src/app/model/InternationalSummarySearch';
import { AuthguardService } from 'src/app/auth-service/authguard/authguard.service';
import { ProductField } from 'src/app/model/ProductField';
import { ProductFieldServiceService } from 'src/app/page/product-field/service/product-field-service.service';

@Component({
  selector: 'app-international-summary-by-road',
  templateUrl: './international-summary-by-road.component.html',
  styleUrls: ['./international-summary-by-road.component.scss'],
  providers: [DatePipe, MessageService]
})
export class InternationalSummaryByRoadComponent {

  name!: string;
  bound!: Bound[];
  // shipmentStatus!: ShipmentStatus[];
  shipmentStatus!: ProductField | null | undefined;
  role: any;

  selectedBound: Bound = {
    bound: "In bound"
  };

  search: any = {
    fromDate: null,
    toDate: null,
    status: null,
    origin: null,
    destination: null,
    type: null
  }
  paginationRes: any;
  page: any;
  size: any;

  constructor(
    private summaryService: SummaryService,
    private messageService: MessageService,
    private datePipe: DatePipe,
    private authguardService: AuthguardService,
    // private shipmentStatusService: ShipmentStatusService,
    private shipmentStatusService: ProductFieldServiceService
    ) { }

  internationalShipmentByRoad: any = [];
  items: MenuItem[] | undefined;

  ngOnInit() {
    this.getRole()

    this.items = [{ label: 'International Summary By Road' }];
    this.bound = [
      {
        bound: "In bound"
      },
      {
        bound: "Out bound"
      }
    ]
    this.getAllShipmentStatus();
    this.getInboundSummary(this.search, 0, 10);
  }
  getRole() {
    const token = localStorage.getItem('accessToken');

    const decodeToken = this.authguardService.getDecodedAccessToken(token!);

    this.role = decodeToken.ROLES;
    console.log(this.role);

  }

  onBoundChange() {
    this.search = {
      fromDate: null,
      toDate: null,
      status: null,
      origin: null,
      destination: null,
      type: null
    }
    if (this.selectedBound && this.selectedBound.bound === "In bound") {
      this.getInboundSummary(this.search, 0, 10);
    } else if (this.selectedBound && this.selectedBound.bound === "Out bound") {
      this.getOutboundSummary(this.search, 0, 10);
    }
  }

  getAllShipmentStatus() {
    this.shipmentStatusService.getProductFieldByName("Search_For_International_By_Road").subscribe((res: ProductField) => {
      this.shipmentStatus = res;
    }, error => {
      this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
    })
  }

  getInboundSummary(obj: InternationalSummarySearch, page: number, size: number) {
    this.summaryService.getInboundSummaryForRoad(obj, page, size).subscribe((res: any) => {
      this.internationalShipmentByRoad = res.content;
      this.paginationRes = res;
      this.search = {
        fromDate: null,
        toDate: null,
        status: null,
        origin: null,
        destination: null,
        type: null
      }
    }, (error: any) => {

      this.internationalShipmentByRoad = [];
      this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });

    })
  }

  getOutboundSummary(obj: InternationalSummarySearch, page: number, size: number) {
    this.summaryService.getOutboundSummaryForRoad(obj, page, size).subscribe((res: any) => {
      this.internationalShipmentByRoad = res.content;
      this.paginationRes = res;
      this.search = {
        fromDate: null,
        toDate: null,
        status: null,
        origin: null,
        destination: null,
        type: null
      }
    }, (error: any) => {

      this.internationalShipmentByRoad = [];
      this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });

    })
  }

  onSubmit() {
    if (this.search.destination === null) {
      this.search.destination = "";
    }
    if (this.search.origin === null) {
      this.search.origin = "";
    }
    if (this.search.status === null) {
      this.search.status = "";
    }
    if (this.search.status === null) {
      this.search.type = "";
    }
    if (this.search.toDate === null || this.search.toDate === "") {
      this.search.toDate = "";
    }
    else {
      let originalDate = new Date(this.search.toDate);
      this.search.toDate = this.datePipe.transform(originalDate, 'yyyy-MM-dd');
    }
    if (this.search.fromDate === null || this.search.fromDate === "") {
      this.search.fromDate = "";
    }
    else {
      let originalDate = new Date(this.search.fromDate);
      this.search.fromDate = this.datePipe.transform(originalDate, 'yyyy-MM-dd');
    }

    if (this.selectedBound.bound === "In bound") {
      this.getInboundSummary(this.search, 0, 10);
    } else {
      this.getOutboundSummary(this.search, 0, 10);

    }
  }
  onPageChange(event: any) {
    this.page = event.page;
    this.size = event.rows;
    this.getInboundSummary(this.search, this.page, this.size);
    this.getOutboundSummary(this.search, this.page, this.size);
  }
}

interface Bound {
  bound: string
}
