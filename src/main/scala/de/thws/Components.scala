package de.thws

import de.thws.database.{JdbcConnectionsComponent, MigrationComponent, TransactionServiceComponent}
import de.thws.repository.{WafflePriceRepositoryComponent, WaffleTransactionsRepositoryComponent}
import de.thws.route.{MarketplaceRouteComponent, TradingRouteComponent, WaffleTradingRouteComponent}
import de.thws.service.{WafflePriceServiceComponent, WafflePriceUpdateServiceComponent, WaffleTransactionServiceComponent}
import de.thws.database.JdbcConnectionsComponent
import de.thws.json.TradeRequestJsonFormatComponent

trait Components
  extends
    MigrationComponent,
    JdbcConnectionsComponent,
    DatabaseConfigurationComponent,
    // Services
    TransactionServiceComponent,
    WaffleTransactionServiceComponent,
    WafflePriceServiceComponent,
    WafflePriceUpdateServiceComponent,
    // Repositories
    WaffleTransactionsRepositoryComponent,
    WafflePriceRepositoryComponent,
    // Routes
    WaffleTradingRouteComponent,
    MarketplaceRouteComponent,
    TradingRouteComponent,
    // Json
    TradeRequestJsonFormatComponent
