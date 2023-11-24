import { InternationalRouteService } from './../../service/international-route.service';
import { MenuItem, MessageService } from 'primeng/api';
import { Component } from '@angular/core';
import { Routes } from '../../../../model/ShipmentRoutes';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-update-international-road-routes',
  templateUrl: './update-international-road-routes.component.html',
  styleUrls: ['./update-international-road-routes.component.scss'],
  providers: [MessageService]
})
export class UpdateInternationalRoadRoutesComponent {

  items: MenuItem[] | undefined;
  routes: Routes = {
    id: null,
    destination: null,
    driver: null,
    eta: null,
    etd: null,
    origin: null,
    route: null,
  }


  routeNumbers: any;

  domesticRouteId: any;
  showDropDown: boolean = false;

  constructor(private internationalRouteService: InternationalRouteService,
    private router: Router,
    private messageService: MessageService,
    private route: ActivatedRoute,
    // private datePipe: DatePipe
  ) { }


  getInternationalRoute() {
    this.showDropDown = true;
    this.routeNumbers = []

    if (this.routes.origin !== null && this.routes.destination !== null) {
      this.internationalRouteService.getInternationalRoute(this.routes.origin!, this.routes.destination!).subscribe((res: any) => {
        this.routes = res;

      }, (error: any) => {
        console.log(error);
        this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
      })

    } else {
      this.messageService.add({ severity: 'error', summary: 'Error', detail: 'You must have to select origin and destination port' });
    }
  }

  ngOnInit(): void {
    this.domesticRouteId = +this.route.snapshot.paramMap.get('id')!;
    this.items = [{ label: 'International Shipment Routes For Road', routerLink: '/international-routes-for-road' }, { label: 'Edit International Shipment Routes For Road' }];

    this.internationalRouteService.getInternationalRouteById(this.domesticRouteId).subscribe(res => {
    }, err => { });
  }


  getAllInternationalRoutes() {
    this.internationalRouteService.getAllInternationalRoutesForAir().subscribe((res: Routes[]) => {
      this.routeNumbers = res;
    }, error => {
      if (error.error.body) {
        this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
      } else {
        this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error });
      }
    })
  }

  onSubmit() {
    // this.routes.etd = this.datePipe.transform(this.routes.etd, 'yyyy-MM-dd')
    // this.routes.eta = this.datePipe.transform(this.routes.eta, 'yyyy-MM-dd')
  }

}
