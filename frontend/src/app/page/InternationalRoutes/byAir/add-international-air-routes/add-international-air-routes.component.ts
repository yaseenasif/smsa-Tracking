import { InternationalShipment } from './../../../../model/InternationalShipment';
import { Component } from '@angular/core';
import { MenuItem, MessageService } from 'primeng/api';
import { Routes } from '../../../../model/ShipmentRoutes';
import { InternationalRouteService } from '../../service/international-route.service';
import { Router } from '@angular/router';
import { DomesticShipment } from '../../../../model/DomesticShipment';
import { LocationPort } from '../../../../model/LocationPort';
import { Location } from '../../../../model/Location';
import { LocationService } from '../../../location/service/location.service';

@Component({
  selector: 'app-add-international-air-routes',
  templateUrl: './add-international-air-routes.component.html',
  styleUrls: ['./add-international-air-routes.component.scss'],
  providers: [MessageService]
})
export class AddInternationalAirRoutesComponent {
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

  internationalShipment: InternationalShipment = {
    id: null,
    actualWeight: null,
    arrivalDate: null,
    arrivalTime: null,
    ata: null,
    attachments: null,
    carrier: null,
    departureDate: null,
    departureTime: null,
    destinationCountry: null,
    destinationPort: null,
    driverContact: null,
    driverName: null,
    flightNumber: null,
    numberOfBags: null,
    numberOfPallets: null,
    numberOfShipments: null,
    originCountry: null,
    originPort: null,
    overageAWBs: null,
    overages: null,
    preAlertNumber: null,
    received: null,
    referenceNumber: null,
    refrigeratedTruck: false,
    remarks: null,
    sealNumber: null,
    shipmentMode: null,
    shortageAWBs: null,
    shortages: null,
    status: 'Pre-Alert Created',
    tagNumber: null,
    totalShipments: null,
    type: 'By Air',
    vehicleNumber: null,
    vehicleType: null,
    routeNumber: null,
    etd: null,
    eta: null,
    atd: null,
    trip: null,
  }

  location!: Location[];
  selectedLocation!: Location;
  selectedOriginLocation!: Location;
  selectedDestinationLocation!: Location;


  routeNumbers: any;
  minETDDate: Date = new Date();
  destination!: LocationPort[];

  constructor(
    private internationalRouteService: InternationalRouteService,
    private internationalService: LocationService,
    private messageService: MessageService,
    private router: Router) { }


  ngOnInit(): void {
    this.items = [{ label: 'International Route List For Air', routerLink: '/international-routes-for-air' }, { label: 'Add Route' }];
    this.getInternationalLocations();
  }

  getInternationalLocations() {
    this.internationalService.getAllLocationForInternational().subscribe((res: Location[]) => {
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
    this.internationalRouteService
      .addInternationalRoute(this.routes).subscribe(res => {
        this.messageService.add({ severity: 'success', summary: 'Success', detail: 'Location is added' });
        setTimeout(() => {
          this.router.navigate(['/international-route']);
        }, 800);
      }, error => {
        this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Route is not added' });
      })
  }

  onETDDateSelected(selectedETDDate: Date) {
    const minETDDate = new Date(selectedETDDate);

    minETDDate.setDate(minETDDate.getDate() + 1);

    this.minETDDate = minETDDate;
  }


}

