import { Component } from '@angular/core';
import { MenuItem, MessageService } from 'primeng/api';
import { Routes } from '../../../model/ShipmentRoutes';
import { ActivatedRoute, Router } from '@angular/router';
import { DomesticRoutesService } from '../service/domestic-routes.service';
// import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-update-domestic-routes',
  templateUrl: './update-domestic-routes.component.html',
  styleUrls: ['./update-domestic-routes.component.scss'],
  providers: [MessageService]
})
export class UpdateDomesticRoutesComponent {

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

  drivers: any[] = ["Domestic", "International"];

  routeNumbers: any;

  domesticRouteId: any;
  showDropDown: boolean = false;

  constructor(private domesticRouteService: DomesticRoutesService,
    private router: Router,
    private messageService: MessageService,
    private route: ActivatedRoute,
    // private datePipe: DatePipe
  ) { }


  getDomesticRoute() {
    this.showDropDown = true;
    this.routeNumbers = []

    if (this.routes.origin !== null && this.routes.destination !== null) {
      this.domesticRouteService.getDomesticRoute(this.routes.origin!, this.routes.destination!).subscribe((res: any) => {
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
    this.items = [{ label: 'Domestic Shipment Routes', routerLink: '/domestic-routes' }, { label: 'Edit Domestic Shipment Routes' }];

    this.domesticRouteService.getDomesticRouteById(this.domesticRouteId).subscribe(res => {
     }, err => { });
  }


  getAllDomesticRoutes() {
    this.domesticRouteService.getAllDomesticRoutes().subscribe((res: Routes[]) => {
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





