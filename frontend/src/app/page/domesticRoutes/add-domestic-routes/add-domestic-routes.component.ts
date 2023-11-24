import { LocationPort } from '../../../model/LocationPort';
import { LocationPortService } from '../../location-port/service/location-port.service';
import { LocationService } from '../../../page/location/service/location.service';
import { MenuItem, MessageService } from 'primeng/api';
import { Component } from '@angular/core';
import { Routes } from '../../../model/ShipmentRoutes';
import { DomesticRoutesService } from '../service/domestic-routes.service';
import { Router } from '@angular/router';
import { Location } from '../../../model/Location';
import { DomesticShipment } from '../../../model/DomesticShipment';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-add-domestic-routes',
  templateUrl: './add-domestic-routes.component.html',
  styleUrls: ['./add-domestic-routes.component.scss'],
  providers: [MessageService, DatePipe]
})
export class AddDomesticRoutesComponent {

  items: MenuItem[] | undefined;
  domesticRoutes: Routes = {
    id: null,
    destination: null,
    driver: null,
    eta: null,
    etd: null,
    origin: null,
    route: null,
  }

  location!: Location[];
  selectedLocation!: Location;
  selectedOriginLocation!: Location;
  selectedDestinationLocation!: Location;


  routeNumbers: any;
  minETDDate: Date = new Date();
  destination!: LocationPort[];

  constructor(
    private domesticRouteService: DomesticRoutesService,
    private domesticLocation: LocationService,
    private messageService: MessageService,
    private datePipe: DatePipe,
    private router: Router) { }


  ngOnInit(): void {
    this.items = [{ label: 'Domestic Route List', routerLink: '/domestic-routes' }, { label: 'Add Route' }];
    this.getDomesticLocations();
  }

  getDomesticLocations() {
    this.domesticLocation.getAllLocationForDomestic().subscribe((res: Location[]) => {
      this.location = res.filter(el => el.status);
    }, error => {
      if (error.error.body) {
        this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
      } else {
        this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error });
      }
    })
  }

  onSubmit() {
    this.domesticRoutes.etd = this.datePipe.transform(this.domesticRoutes.etd, 'HH:mm:ss')
    this.domesticRoutes.eta = this.datePipe.transform(this.domesticRoutes.eta, 'HH:mm:ss')
    this.addDomesticRoutes(this.domesticRoutes);
  }

  addDomesticRoutes(domesticRoutes: Routes) {
    debugger
    this.domesticRouteService.addDomesticRoute(domesticRoutes).subscribe((res: Routes) => {
      this.messageService.add({ severity: 'success', summary: 'Success', detail: 'Domestic Route Added Successfully' });
      setTimeout(() => {
        this.router.navigate(['/domestic-routes']);
      }, 800);
    }, (error: any) => {
      if (error.error.body) {
        this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
      } else {
        this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error });
      }
    })
  }

  onETDDateSelected(selectedETDDate: Date) {
    const minETDDate = new Date(selectedETDDate);

    minETDDate.setDate(minETDDate.getDate() + 1);

    this.minETDDate = minETDDate;
  }

}

