import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AdmniTemplateComponent } from './admni-template/admni-template.component';

// Importation des modules Angular Material nécessaires
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatIconModule} from '@angular/material/icon';
import {MatMenuModule} from '@angular/material/menu';
import { HomeComponent } from './home/home.component';
import { DescriptionComponent } from './description/description.component';
import { InfoComponent } from './info/info.component';
import {MatDrawer, MatDrawerContainer} from '@angular/material/sidenav';

@NgModule({
  declarations: [
    AppComponent,
    AdmniTemplateComponent,
    HomeComponent,
    DescriptionComponent,
    InfoComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatToolbarModule,
    MatButtonModule,
    MatIconModule,
    MatMenuModule,
    MatDrawer,
    MatDrawerContainer


  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
