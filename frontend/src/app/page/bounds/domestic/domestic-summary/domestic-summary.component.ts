import { Component } from '@angular/core';
import { MenuItem, MessageService } from 'primeng/api';
import { SummaryService } from '../../service/summary.service';
import { SummarySearch } from '../../../../model/summarySearch';
import { DatePipe } from '@angular/common';
import { ShipmentStatusService } from '../../../shipment-status/service/shipment-status.service';
import { ShipmentStatus } from 'src/app/model/ShipmentStatus';
import { AuthguardService } from 'src/app/auth-service/authguard/authguard.service';

@Component({
  selector: 'app-domestic-summary',
  templateUrl: './domestic-summary.component.html',
  styleUrls: ['./domestic-summary.component.scss'],
  providers: [MessageService, DatePipe]
})
export class DomesticSummaryComponent {
  name!: string;
  bound!: Bound[];
  shipmentStatus!: ShipmentStatus[];
  role: any;

  selectedBound: Bound = {
    bound: "In bound"
  };
  page: number = 0;
  size: number = 10;
  paginationRes: any;

  constructor(private summaryService: SummaryService,
    private datePipe: DatePipe,
    private messageService: MessageService,
    private authguardService: AuthguardService,
    private shipmentStatusService: ShipmentStatusService) { }
  domesticShipment: any = [];
  items: MenuItem[] | undefined;

  search: any = {
    fromDate: null,
    toDate: null,
    status: null,
    origin: null,
    destination: null
  }

  onBoundChange() {
    this.search = {
      fromDate: null,
      toDate: null,
      status: null,
      origin: null,
      destination: null
    }
    if (this.selectedBound && this.selectedBound.bound === "In bound") {
      this.getInboundSummary(this.search, 0, 10);
    } else if (this.selectedBound && this.selectedBound.bound === "Out bound") {
      this.getOutboundSummary(this.search, 0, 10);
    }
  }


  ngOnInit() {
    this.getRole()
    this.getAllShipmentStatus();
    this.getInboundSummary(this.search, 0, 10);
    this.items = [{ label: 'Domestic Summary' }];
    this.bound = [
      {
        bound: "In bound"
      },
      {
        bound: "Out bound"
      }
    ]
  }

  getRole() {
    const token = localStorage.getItem('accessToken');

    const decodeToken = this.authguardService.getDecodedAccessToken(token!);

    this.role = decodeToken.ROLES;
    console.log(this.role);

  }

  getInboundSummary(obj: SummarySearch, page: number, size: number) {
    debugger
    this.summaryService.getInboundSummary(obj, page, size).subscribe((res: any) => {

      this.domesticShipment = res.content;
      this.paginationRes = res;
      // this.search = {
      //   fromDate: null,
      //   toDate: null,
      //   status: null,
      //   origin: null,
      //   destination: null
      // }
    }, (error: any) => {

      if (error.error.body) {
        this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
      } else {
        this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error });
      }
      this.domesticShipment = [];
    })
  }

  getOutboundSummary(obj: SummarySearch, page: number, size: number) {
    this.summaryService.getOutboundSummary(obj, page, size).subscribe((res: any) => {
      this.domesticShipment = res.content;
      // this.search = {
      //   fromDate: null,
      //   toDate: null,
      //   status: null,
      //   origin: null,
      //   destination: null
      // }
    }, (error: any) => {
      if (error.error.body) {
        this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
      } else {
        this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error });
      }
      this.domesticShipment = [];
    })
  }

  getAllShipmentStatus() {
    this.shipmentStatusService.getALLShipmentStatus().subscribe((res: ShipmentStatus[]) => {
      this.shipmentStatus = res;
    }, error => {


    })
  }
  onSubmit() {
    debugger
    if (this.search.destination === null) {
      this.search.destination = "";
    }
    if (this.search.origin === null) {
      this.search.origin = "";
    }
    if (this.search.status === null) {
      this.search.status = "";
    }
    if (this.search.toDate === "" || this.search.toDate === null) {
      this.search.toDate = "";
    }
    else {
      let originalDate = new Date(this.search.toDate);
      this.search.toDate = this.datePipe.transform(originalDate, 'yyyy-MM-dd');
    }
    if (this.search.fromDate === "" || this.search.fromDate === null) {
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
    this.getOutboundSummary(this.search, this.page, this.size)
  }
}

interface Bound {
  bound: string
}


